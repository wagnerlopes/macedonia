package br.com.wagnersoft.macedonia.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {

  @Test
  void testGettersAndSetters() {
    Contrato contrato = new Contrato();
    LocalDate inicio = LocalDate.now();
    LocalDate termino = LocalDate.now().plusYears(1);
    Ocs ocs = new Ocs();

    contrato.setId(1);
    contrato.setInicioData(inicio);
    contrato.setTerminoData(termino);
    contrato.setChQtd(120);
    contrato.setOcs(ocs);

    assertEquals(1, contrato.getId());
    assertEquals(inicio, contrato.getInicioData());
    assertEquals(termino, contrato.getTerminoData());
    assertEquals(120, contrato.getChQtd());
    assertEquals(ocs, contrato.getOcs());
  }

  @Test
  void testEqualsAndHashCode() {
    Contrato contrato1 = new Contrato();
    contrato1.setId(1);

    Contrato contrato2 = new Contrato();
    contrato2.setId(1);

    Contrato contratoDiferente = new Contrato();
    contratoDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(contrato1, contrato1);
    assertEquals(contrato1, contrato2);
    assertEquals(contrato1.hashCode(), contrato2.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(contrato1, contratoDiferente);
    assertNotEquals(contrato1.hashCode(), contratoDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, contrato1);
    assertNotEquals("Uma String qualquer", contrato1);
  }

  @Test
  void testToString() {
    Contrato contrato = new Contrato();
    contrato.setId(5);

    // Verifica se o toString do Lombok gera uma string contendo informações essenciais
    String toStringResult = contrato.toString();
    assertTrue(toStringResult.contains("Contrato"));
    assertTrue(toStringResult.contains("id=5"));
  }
}
