package br.com.wagnersoft.macedonia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@ExtendWith(MockitoExtension.class)
class GuiaEncaminhamentoServiceTest {

  @Mock
  private GuiaEncaminhamentoRepository rep;

  @Mock
  private ProcedimentoMedicoRepository pmRep;

  @InjectMocks
  private GuiaEncaminhamentoService service;

  private GuiaEncaminhamento b1;

  private GuiaEncaminhamento b2;

  public GuiaEncaminhamentoServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new GuiaEncaminhamento();
    b1.setId(1);
    b1.setGuiaNr(1);

    b2 = new GuiaEncaminhamento();
    b2.setId(2);
    b2.setGuiaNr(2);
  }

  @Test
  void findById_deveRetornarObjetoQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<GuiaEncaminhamento> result = service.findById(1);

    assertThat(result).isPresent()
    .get()
    .extracting(GuiaEncaminhamento::getId)
    .isEqualTo(1);

  }

  @Test
  void findById_deveRetornarEmptyQuandoIdNulo() {
    Optional<GuiaEncaminhamento> result = service.findById(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findByNr_deveRetornarObjetoQuandoExiste() {

    when(rep.findByGuiaNr(1)).thenReturn(List.of(b1));

    List<GuiaEncaminhamento> result = service.findByGuiaNr(1);

    assertThat(result).isNotEmpty()
    .first()
    .extracting(GuiaEncaminhamento::getGuiaNr)
    .isEqualTo(1);

  }

  @Test
  void findByNr_deveRetornarEmptyQuandoIdNulo() {
    List<GuiaEncaminhamento> result = service.findByGuiaNr(null);
    assertThat(result).isEmpty();
  }

  @Test
  void countByAno_deveRetornarSoma() {
    List<Object[]> mockResult = new ArrayList<>();
    mockResult.add(new Object[] {1, "Janeiro", 100});
    mockResult.add(new Object[] {2, "Fevereiro", 200});

    when(rep.countByMonth(2026)).thenReturn(mockResult);

    List<Object[]> result = service.countByMonth(2026);
    assertThat(result).isNotEmpty();
  }

  @Test
  void listAll_deveRetornarTodosObjetos() {

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1,b2));

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
  void remove_deveExcluirObjetoExistente() {

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
  void add_deveAtualizarObjetoExistente() {

    when(rep.findById(2)).thenReturn(Optional.of(b2));
    b2.setObservacao("XXX");

    service.add(b2);

    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getObservacao().equals("XXX")));

  }

  @Test
  void add_deveExecutarBlocoIfQuandoHaProcedimentos() {
      // Arrange
      GuiaEncaminhamento existing = new GuiaEncaminhamento();
      existing.setId(1);

      GuiaEncaminhamento replacement = new GuiaEncaminhamento();
      replacement.setId(1);

      // Procedimento simulado
      ProcedimentoMedico pm = new ProcedimentoMedico();
      pm.setId(1);

      GuiaPm guiaPm = new GuiaPm();
      guiaPm.setId(1);
      guiaPm.setValorTotal(new BigDecimal("200.00"));
      guiaPm.setPm(pm);

      // Adiciona o procedimento na lista já inicializada
      replacement.getProcedimentos().add(guiaPm);

      // Mock do repositório
      when(rep.findById(1)).thenReturn(Optional.of(existing));
      when(pmRep.findById(1)).thenReturn(Optional.of(pm));

      // Act
      service.add(replacement);

      // Assert
      assertThat(existing.getValorTotal()).isEqualByComparingTo("200.00");
      assertThat(existing.getProcedimentos()).hasSize(1);
      assertThat(existing.getProcedimentos().get(0).getPm()).isEqualTo(pm);

      verify(rep).save(existing);
  }

  @Test
  void add_deveSalvarBeneficiarioInexistente() {
    when(rep.findById(2)).thenReturn(Optional.empty());
    service.add(b2);
    verify(rep).save(b2);
  }

}
