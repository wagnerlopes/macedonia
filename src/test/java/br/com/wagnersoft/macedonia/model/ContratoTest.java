package br.com.wagnersoft.macedonia.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ContratoTest {

  Contrato contrato;

  @BeforeEach
  void setUp() {
    contrato = new Contrato();
    contrato.setId(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    LocalDate inicio = LocalDate.now();
    LocalDate termino = LocalDate.now().plusYears(1);
    Ocs ocs = new Ocs();

    contrato.setInicioData(inicio);
    contrato.setTerminoData(termino);
    contrato.setChQtd(1);
    contrato.setOcs(ocs);

    assertEquals(1, contrato.getId());
    assertEquals(inicio, contrato.getInicioData());
    assertEquals(termino, contrato.getTerminoData());
    assertEquals(1, contrato.getChQtd());
    assertEquals(ocs, contrato.getOcs());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {

    // Arrange
    Contrato igual = new Contrato();
    igual.setId(1);

    Contrato diferente = new Contrato();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(contrato.equals(contrato));
    assertTrue(contrato.equals(igual));
    assertEquals(contrato.hashCode(), igual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertFalse(contrato.equals(diferente));
    assertNotEquals(contrato.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(contrato.equals(null));
    assertNotEquals(contrato, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    // Verifica se o toString do Lombok gera uma string contendo informações essenciais
    String toStringResult = contrato.toString();
    assertTrue(toStringResult.contains("Contrato"));
    assertTrue(toStringResult.contains("id=1"));
  }
}
