package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProtocoloTest {

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    Protocolo protocolo = new Protocolo();
    Ocs ocs = new Ocs();
    LocalDate data = LocalDate.of(2020, 1, 1);

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
    Protocolo protocolo1 = new Protocolo();
    protocolo1.setId(1);

    Protocolo protocolo2 = new Protocolo();
    protocolo2.setId(1);

    Protocolo dthDiferente = new Protocolo();
    dthDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(protocolo1, protocolo1);
    assertEquals(protocolo1, protocolo2);
    assertEquals(protocolo1.hashCode(), protocolo2.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(protocolo1, dthDiferente);
    assertNotEquals(protocolo1.hashCode(), dthDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, protocolo1);
    assertNotEquals("Uma String qualquer", protocolo1);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    Protocolo protocolo = new Protocolo();
    protocolo.setId(1);
    String toStringResult = protocolo.toString();
    assertTrue(toStringResult.contains("Protocolo"));
    assertTrue(toStringResult.contains("id=1"));
  }

}
