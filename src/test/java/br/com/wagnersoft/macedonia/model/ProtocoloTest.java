package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProtocoloTest {

  private Protocolo protocolo;

  @BeforeEach
  void setUp() {
    protocolo = new Protocolo();
    protocolo.setId(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    // Arrange
    Ocs ocs = new Ocs();
    LocalDate data = LocalDate.of(2020, 1, 1);

    // Act
    protocolo.setId(1);
    protocolo.setAssunto("A");
    protocolo.setDestino("D");
    protocolo.setDocData(data);
    protocolo.setDocNr("N");
    protocolo.setDocTipo("T");
    protocolo.setObservacao("O");
    protocolo.setOcs(ocs);
    protocolo.setStatus(1);
    protocolo.setValor(BigDecimal.ONE);

    // Assert
    assertEquals(1, protocolo.getId());
    assertEquals(data, protocolo.getDocData());
    assertEquals("A", protocolo.getAssunto());
    assertEquals("D", protocolo.getDestino());
    assertEquals("N", protocolo.getDocNr());
    assertEquals("T", protocolo.getDocTipo());
    assertEquals("O", protocolo.getObservacao());
    assertEquals(1, protocolo.getStatus());
    assertEquals(BigDecimal.ONE, protocolo.getValor());
    assertEquals(ocs, protocolo.getOcs());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {
    // Arrange
    Protocolo igual = new Protocolo();
    igual.setId(1);

    Protocolo diferente = new Protocolo();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(protocolo.equals(protocolo));
    assertTrue(protocolo.equals(igual));
    assertEquals(protocolo.hashCode(), igual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(protocolo, diferente);
    assertNotEquals(protocolo.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(protocolo.equals(null));
    assertNotEquals(protocolo, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = protocolo.toString();
    assertTrue(toStringResult.contains("Protocolo"));
    assertTrue(toStringResult.contains("id=1"));
  }

  @Test
  @DisplayName("Deve adicionar e remover guia mantendo relacionamento bidirecional")
  void deveAdicionarRemoverGuiaMantendoRelacionamentoBidirecional() {

    GuiaEncaminhamento guia = new GuiaEncaminhamento();

    protocolo.addGuia(guia);

    assertAll(
        () -> assertTrue(protocolo.getGuias().contains(guia)),
        () -> assertEquals(guia.getProtocolo(), protocolo)
        );

    assertTrue(protocolo.addGuia(guia).equals(guia));
    
    protocolo.removeGuia(guia);

    assertAll(
        () -> assertTrue(protocolo.getGuias().isEmpty()),
        () -> assertNull(guia.getProtocolo())
        );
  }

}
