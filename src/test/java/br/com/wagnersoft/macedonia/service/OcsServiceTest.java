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
  void findById_deveRetornarOcsQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<Ocs> result = service.findById(1);

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
  void findByDescricao_deveRetornarListaComDescricaoPesquisado() {
    when(rep.findByDescricao("OCS 2")).thenReturn(List.of(b2));
    var lista = service.findByDescricao("OCS 2");
    assertThat(lista).isNotEmpty().containsExactly(b2);
  }

  @Test
  void findByDescricao_deveRetornarListaVaziaQuandoDescricaoNulo() {
    List<Ocs> result = service.findByDescricao(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByDescricao_deveRetornarListaVaziaQuandoDescricaoVazio() {
    List<Ocs> result = service.findByDescricao("   ");
    assertThat(result).isEmpty();
  }

  @Test
  void mapAll_deveRetornarMapaComIdEDescricao() {
    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b2));
    var mapa = service.mapAll();
    assertThat(mapa).containsEntry(1, "OCS 1")
    .containsEntry(2, "OCS 2")
    .hasSize(2);
  }

  @Test
  void mapAll_deveRetornarMapaVazioQuandoNaoHaOcss() {
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

    // Act
    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b3));
    var mapa = service.mapAll();

    // Assert
    assertThat(mapa).containsEntry(1, "OCS 1").hasSize(1);
  }

  @Test
  void remove_deveIgnorarIdNulo() {
    service.remove(null);
    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveIgnorarOcsExistenteComGuia() {
    b2.addGuia(new GuiaEncaminhamento());
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.remove(2);
    verify(rep, never()).delete(any());
  }

  @Test
  void remove_deveExcluirOcsExistente() {
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

    // Act
    when(rep.findById(2)).thenReturn(Optional.empty());
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

    // Act
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.add(b2);

    // Arrange
    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getDescricao().equals("OCS Atualizado")));
  }

  @Test
  void add_deveSalvarOcsExistenteComProcedimento() {
    // Arrange
    ProcedimentoMedico pm = new ProcedimentoMedico();
    OcsPm opm = new OcsPm();
    opm.setPm(pm);

    b2.addOcsPm(opm);
    b2.setDescricao("OCS Atualizado");

    // Act
    when(rep.findById(2)).thenReturn(Optional.of(b2));
    service.add(b2);

    // Arrange
    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getDescricao().equals("OCS Atualizado")));
  }

  @Test
  void addProcedimento_deveIgnorarNulo() {
    service.addProcedimentoMedico(null);
    verify(rep, never()).save(any());
  }

  @Test
  void addProcedimento_deveInluirProcedimentoOcs() {
    // Arrange
    OcsPm opm = new OcsPm();
    opm.setPm(new ProcedimentoMedico());
    opm.setOcs(b1);

    // Act
    when(rep.findById(1)).thenReturn(Optional.of(b1));
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
  void removeProcedimento_deveInluirProcedimentoOcs() {
    // Arrange
    OcsPm opm = new OcsPm();
    opm.setPm(new ProcedimentoMedico());
    opm.setOcs(b1);

    // Act
    when(rep.findById(1)).thenReturn(Optional.of(b1));
    service.removeProcedimentoMedico(opm);

    // Arrange
    verify(rep).findById(1);
  }

}
