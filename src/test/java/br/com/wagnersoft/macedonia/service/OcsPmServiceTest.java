package br.com.wagnersoft.macedonia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.OcsPmRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@ExtendWith(MockitoExtension.class)
class OcsPmServiceTest {

  @Mock
  private OcsPmRepository rep;

  @Mock
  private ProcedimentoMedicoRepository pmRep;

  @InjectMocks
  private OcsPmService service;

  private OcsPm b1;

  private OcsPm b2;

  public OcsPmServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new OcsPm();
    b1.setId(1);
    b1.setUnidadeMedida("Uso 1");

    b2 = new OcsPm();
    b2.setId(2);
    b2.setUnidadeMedida("Uso 2");
  }

  @Test
  void findById_deveRetornarOcsPmQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<OcsPm> result = service.findById(1);

    assertThat(result).isPresent()
    .get()
    .extracting(OcsPm::getUnidadeMedida)
    .isEqualTo("Uso 1");
  }

  @Test
  void findById_deveRetornarEmptyQuandoIdNulo() {
    Optional<OcsPm> result = service.findById(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByOcs_deveRetornarListaVaziaQuandoOcsNulo() {
    List<OcsPm> result = service.findByOcs(null);
    assertThat(result).isEmpty();
  }
  
  @Test
  void findByOcs_deveRetornarOcsPmQuandoOcsExiste() {

    Ocs ocs = new Ocs();
    ocs.setId(1);
    
    when(rep.findByOcs(ocs)).thenReturn(List.of(b1));

    List<OcsPm> result = service.findByOcs(ocs);

    assertThat(result).isNotEmpty()
    .contains(b1)
    .hasSize(1);
  }
  
  @Test
  void findByPm_deveRetornarListaVaziaQuandoPmNulo() {
    List<OcsPm> result = service.findByPm(null);
    assertThat(result).isEmpty();
  }
  
  @Test
  void findByPm_deveRetornarOcsPmQuandoPmExiste() {

    ProcedimentoMedico pm = new ProcedimentoMedico();
    pm.setId(1);
    
    when(rep.findByPm(pm)).thenReturn(List.of(b1));

    List<OcsPm> result = service.findByPm(pm);

    assertThat(result).isNotEmpty()
    .contains(b1)
    .hasSize(1);
  }

  @Test
  void findByOcsPm_deveRetornarListaVaziaQuandoOcsPmNulo() {
    Optional<OcsPm> result = service.findByOcsPm(null, null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByOcsPm_deveRetornarListaVaziaQuandoOcsNuloPmExiste() {
    Optional<OcsPm> result = service.findByOcsPm(null, 1);
    assertThat(result).isEmpty();
  }

  @Test
  void findByOcsPm_deveRetornarListaVaziaQuandoOcsExistePmNulo() {
    Optional<OcsPm> result = service.findByOcsPm(1, null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByOcsPm_deveRetornarObjetoQuandoOcsPmExiste() {
    when(rep.findByOcsPm(1,1)).thenReturn(Optional.of(b1));

    Optional<OcsPm> result = service.findByOcsPm(1, 1);

    assertThat(result).isPresent()
    .get()
    .extracting(OcsPm::getUnidadeMedida)
    .isEqualTo("Uso 1");
  }
  
  @Test
  void listAll_deveRetornarTodosObjetos() {

    when(rep.findAll()).thenReturn(List.of(b1,b2));

    var lista = service.listAll();

    assertThat(lista).isNotEmpty()
    .contains(b1)
    .contains(b2)
    .hasSize(2);
  }
  
  @Test
  void remove_deveIgnorarIdNulo() {
    service.remove(null);
    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveIgnorarOcsGuiaExistente() {
    Ocs ocs = new Ocs();
    ocs.setId(1);
    ocs.addOcsPm(b2);
    ocs.addGuia(new GuiaEncaminhamento());
    
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.remove(2);
    
    verify(rep, never()).delete(any());
  }
  
  @Test
  void remove_deveExcluirOcsPmExistente() {
    Ocs ocs = new Ocs();
    ocs.setId(1);
    ocs.addOcsPm(b2);
    
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.remove(2);
    
    verify(rep).delete(b2);
  }

  @Test
  void add_deveIgnorarIdNulo() {
    service.add(null);
    verify(rep, never()).save(any());
  }

  @Test
  void add_deveSalvarOcsPmInexistente() {
    // Act
    when(rep.findById(2)).thenReturn(Optional.empty());
    service.add(b2);

    //Assert
    verify(rep).findById(2);
    verify(rep).save(b2);
  }

  @Test
  void add_deveSalvarOcsPmExistente() {
    // Arrange
    b2.setUnidadeMedida("Uso Atualizado");

    // Act
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.add(b2);

    // Arrange
    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getUnidadeMedida().equals("Uso Atualizado")));
  }

}
