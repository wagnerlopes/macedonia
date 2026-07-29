package br.com.wagnersoft.macedonia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@ExtendWith(MockitoExtension.class)
class ProcedimentoMedicoServiceTest {

  @Mock
  private ProcedimentoMedicoRepository rep;

  @InjectMocks
  private ProcedimentoMedicoService service;

  private ProcedimentoMedico b1;

  private ProcedimentoMedico b2;

  public ProcedimentoMedicoServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new ProcedimentoMedico();
    b1.setId(1);
    b1.setDescricao("PM 1");

    b2 = new ProcedimentoMedico();
    b2.setId(2);
    b2.setDescricao("PM 2");
  }

  @Test
  void findById_deveRetornarObjetoQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<ProcedimentoMedico> result = service.findById(1);

    assertThat(result).isPresent()
    .get()
    .extracting(ProcedimentoMedico::getId)
    .isEqualTo(1);
  }

  @Test
  void findById_deveRetornarEmptyQuandoCpfNulo() {
    Optional<ProcedimentoMedico> result = service.findById(null);
    assertThat(result).isEmpty();
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
  void mapAll_deveRetornarMapaComCodigoEDescricao() {

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b2));

    var mapa = service.mapAll();

    assertThat(mapa).containsEntry(1, "PM 1")
    .containsEntry(2, "PM 2")
    .hasSize(2);
  }

  @Test
  void mapAll_deveRetornarMapaVazioQuandoNaoHaOcupacao() {

    when(rep.findAll(any(Sort.class))).thenReturn(List.of());

    var mapa = service.mapAll();

    assertThat(mapa).isEmpty();
  }

  @Test
  void mapAll_deveManterPrimeiraOcupacaoQuandoCodigoDuplicado() {

    ProcedimentoMedico b3 = new ProcedimentoMedico();
    b3.setId(1);
    b3.setDescricao("Outra");

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b3));

    var mapa = service.mapAll();

    assertThat(mapa).containsEntry(1, "PM 1") // mantém o primeiro
    .hasSize(1);
  }

}
