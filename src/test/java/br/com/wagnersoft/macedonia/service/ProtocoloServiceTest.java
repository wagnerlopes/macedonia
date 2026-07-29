package br.com.wagnersoft.macedonia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.wagnersoft.macedonia.model.Protocolo;
import br.com.wagnersoft.macedonia.repository.ProtocoloRepository;

@ExtendWith(MockitoExtension.class)
class ProtocoloServiceTest {

  @Mock
  private ProtocoloRepository rep;

  @InjectMocks
  private ProtocoloService service;

  private Protocolo b1;

  private Protocolo b2;

  public ProtocoloServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeEach
  void setup() {
    b1 = new Protocolo();
    b1.setId(1);

    b2 = new Protocolo();
    b2.setId(2);
  }

  @Test
  void findById_deveRetornarObjetoQuandoExiste() {

    when(rep.findById(1)).thenReturn(Optional.of(b1));

    Optional<Protocolo> result = service.findById(1);

    assertThat(result).isPresent()
    .get()
    .extracting(Protocolo::getId)
    .isEqualTo(1);

  }

  @Test
  void findById_deveRetornarEmptyQuandoIdNulo() {
    Optional<Protocolo> result = service.findById(null);
    assertThat(result).isEmpty();
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
    // Arrange
    LocalDate now = LocalDate.now();
    Protocolo existing = new Protocolo();
    existing.setId(1);
    existing.setAssunto("Antigo");
    existing.setDocData(now);

    Protocolo replacement = new Protocolo();
    replacement.setId(1);
    replacement.setAssunto("Protocolo 1");
    replacement.setDocData(now);

    // Act
    when(rep.findById(any())).thenReturn(Optional.of(existing));

    service.add(replacement);

    // Assert
    verify(rep).findById(1);
  }

  @Test
  void add_deveSalvarObjetoInexistente() {
    when(rep.findById(2)).thenReturn(Optional.empty());
    service.add(b2);
    verify(rep).save(b2);
  }

}
