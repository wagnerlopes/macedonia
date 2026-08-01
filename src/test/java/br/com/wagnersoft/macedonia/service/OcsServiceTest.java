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
import org.springframework.data.domain.Sort;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.OcsRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@ExtendWith(MockitoExtension.class)
class OcsServiceTest {

  @Mock
  private OcsRepository rep;

  @Mock
  private ProcedimentoMedicoRepository pmRep;

  @InjectMocks
  private OcsService service;

  private Ocs b1;

  private Ocs b2;

  public OcsServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Ocs();
    b1.setId(1);
    b1.setDescricao("OCS 1");

    b2 = new Ocs();
    b2.setId(2);
    b2.setDescricao("OCS 2");
  }

  @Test
  void findById_deveRetornarOcsQuandoIdExiste() {
    // Arrange
    when(rep.findById(1)).thenReturn(Optional.of(b1));

    // Act
    Optional<Ocs> result = service.findById(1);

    // Assert
    assertThat(result).isPresent()
    .get()
    .extracting(Ocs::getDescricao)
    .isEqualTo("OCS 1");
  }

  @Test
  void findById_deveRetornarEmptyQuandoIdNulo() {
    Optional<Ocs> result = service.findById(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByDescricao_deveRetornarListaQuandoDescricaoExiste() {
    when(rep.findByDescricao("OCS 2")).thenReturn(List.of(b2));
    var lista = service.findByDescricao("OCS 2");
    assertThat(lista).isNotEmpty().containsExactly(b2);
  }

  @Test
  void findByDescricao_deveRetornarEmptyQuandoDescricaoNulo() {
    List<Ocs> result = service.findByDescricao(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByDescricao_deveRetornarEmptyQuandoDescricaoVazia() {
    List<Ocs> result = service.findByDescricao("   ");
    assertThat(result).isEmpty();
  }

  @Test
  void mapAll_deveRetornarMapIdDescricao() {
    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b2));
    var mapa = service.mapAll();
    assertThat(mapa).containsEntry(1, "OCS 1")
    .containsEntry(2, "OCS 2")
    .hasSize(2);
  }

  @Test
  void mapAll_deveRetornarEmptyQuandoOcsNaoExiste() {
    when(rep.findAll(any(Sort.class))).thenReturn(List.of());
    var mapa = service.mapAll();
    assertThat(mapa).isEmpty();
  }

  @Test
  void mapAll_deveManterPrimeiroOcsQuandoIdDuplicado() {
    // Arrange
    Ocs b3 = new Ocs();
    b3.setId(1);
    b3.setDescricao("Outro");

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b3));

    // Act
    var mapa = service.mapAll();

    // Assert
    assertThat(mapa).containsEntry(1, "OCS 1")
                    .hasSize(1);
  }

  @Test
  void remove_deveIgnorarIdNulo() {
    service.remove(null);
    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveIgnorarOcsComGuiaEmitida() {
    // Arrange
    GuiaEncaminhamento guia = new GuiaEncaminhamento();
    guia.setId(1);
    b2.addGuia(guia);
    
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    
    // Act
    service.remove(2);
    
    // Assert
    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveExcluirOcsSemGuia() {
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
  void add_deveSalvarOcsInexistente() {
    // Arrange
    OcsPm opm = new OcsPm();
    b2.addOcsPm(opm);

    when(rep.findById(2)).thenReturn(Optional.empty());
    
    // Act
    service.add(b2);

    //Assert
    verify(rep).findById(2);
    verify(rep).save(b2);
  }

  @Test
  void add_deveSalvarOcsExistenteSemProcedimento() {
    // Arrange
    OcsPm opm = new OcsPm();
    opm.setPm(new ProcedimentoMedico());
    b2.addOcsPm(opm);
    b2.setDescricao("OCS Atualizado");

    when(rep.findById(2)).thenReturn(Optional.of(b2));

    // Act
    service.add(b2);

    // Arrange
    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getDescricao().equals("OCS Atualizado")));
  }

  @Test
  void add_deveSalvarOcsExistenteComProcedimento() {
    // Arrange
    ProcedimentoMedico pm = new ProcedimentoMedico();
    pm.setId(100); // ID setado para não ser removido no removeIf

    OcsPm opm = new OcsPm();
    opm.setPm(pm);

    // OCS que será atualizado (replacement)
    Ocs ocsReplacement = new Ocs();
    ocsReplacement.setId(2);
    ocsReplacement.setDescricao("OCS Atualizado");
    ocsReplacement.addOcsPm(opm);

    // OCS que já existe no banco (existing)
    Ocs ocsExisting = new Ocs();
    ocsExisting.setId(2);

    // Mocks do repositório
    when(rep.findById(2)).thenReturn(Optional.of(ocsExisting));
    when(pmRep.findById(100)).thenReturn(Optional.of(pm));

    // Act
    service.add(ocsReplacement);

    // Assert
    verify(rep).findById(2);
    verify(pmRep).findById(100); // Garante que buscou o procedimento no repositório
    verify(rep).save(argThat(saved -> 
    saved.getId().equals(2) && 
    saved.getDescricao().equals("OCS Atualizado") &&
    !saved.getProcedimentos().isEmpty() // Garante que o procedimento foi adicionado
        ));
  }

  @Test
  void addProcedimento_deveIgnorarNulo() {
    service.addProcedimentoMedico(null);
    verify(rep, never()).save(any());
  }

  @Test
  void addProcedimento_deveInluirProcedimento() {
    // Arrange
    OcsPm opm = new OcsPm();
    opm.setPm(new ProcedimentoMedico());
    opm.setOcs(b1);

    when(rep.findById(1)).thenReturn(Optional.of(b1));
    
    // Act
    service.addProcedimentoMedico(opm);

    // Arrange
    verify(rep).findById(1);
  }

  @Test
  void removeProcedimento_deveIgnorarNulo() {
    service.removeProcedimentoMedico(null);
    verify(rep, never()).save(any());
  }

  @Test
  void removeProcedimento_deveExcluirProcedimento() {
    // Arrange
    OcsPm opm = new OcsPm();
    opm.setPm(new ProcedimentoMedico());
    opm.setOcs(b1);

    when(rep.findById(1)).thenReturn(Optional.of(b1));
    
    // Act
    service.removeProcedimentoMedico(opm);

    // Arrange
    verify(rep).findById(1);
  }

}
