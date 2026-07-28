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

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.repository.CboRepository;

@ExtendWith(MockitoExtension.class)
class CboServiceTest {

  @Mock
  private CboRepository rep;

  @InjectMocks
  private CboService service;

  private Cbo b1;

  private Cbo b2;

  public CboServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Cbo();
    b1.setCodigo("1");
    b1.setDescricao("Ocupacao 1");

    b2 = new Cbo();
    b2.setCodigo("2");
    b2.setDescricao("Ocupacao 2");
  }

  @Test
  void findById_deveRetornarCboQuandoExiste() {

    when(rep.findById("1")).thenReturn(Optional.of(b1));

    Optional<Cbo> result = service.findById("1");

    assertThat(result).isPresent()
    .get()
    .extracting(Cbo::getDescricao)
    .isEqualTo("Ocupacao 1");
  }

  @Test
  void findById_deveRetornarEmptyQuandoCpfNulo() {
    Optional<Cbo> result = service.findById(null);
    assertThat(result).isEmpty();
  }

  @Test
  void findById_deveRetornarEmptyQuandoCpfVazio() {
    Optional<Cbo> result = service.findById("   ");
    assertThat(result).isEmpty();
  }

  @Test
  void listAll_deveRetornarTodosCbo() {

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

    assertThat(mapa).containsEntry("1", "Ocupacao 1")
    .containsEntry("2", "Ocupacao 2")
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

    Cbo b3 = new Cbo();
    b3.setCodigo("1");
    b3.setDescricao("Outra");

    when(rep.findAll(any(Sort.class))).thenReturn(List.of(b1, b3));

    var mapa = service.mapAll();

    assertThat(mapa).containsEntry("1", "Ocupacao 1") // mantém o primeiro
    .hasSize(1);
  }

}
