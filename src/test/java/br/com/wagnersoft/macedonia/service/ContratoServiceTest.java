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

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.repository.ContratoRepository;

@ExtendWith(MockitoExtension.class)
class ContratoServiceTest {

  @Mock
  private ContratoRepository rep;

  @InjectMocks
  private ContratoService service;

  private Contrato b1;

  private Contrato b2;

  public ContratoServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Contrato();
    b1.setId(1);

    b2 = new Contrato();
    b2.setId(2);
  }

  @Test
  void findById_deveRetornarObjetoQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<Contrato> result = service.findById(1);

    assertThat(result).isPresent()
    .get()
    .extracting(Contrato::getId)
    .isEqualTo(1);

  }

  @Test
  void findById_deveRetornarEmptyQuandoIdNulo() {
    Optional<Contrato> result = service.findById(null);
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
  void add_deveAtualizarContratoExistente() {

    when(rep.findById(2)).thenReturn(Optional.of(b2));
    b2.setChQtd(10);

    service.add(b2);

    verify(rep).findById(2);
    verify(rep).save(argThat(b -> b.getId().equals(2) && b.getChQtd() == 10));
  }

  @Test
  void add_deveSalvarBeneficiarioInexistente() {
    when(rep.findById(2)).thenReturn(Optional.empty());
    service.add(b2);
    verify(rep).save(b2);
  }

}
