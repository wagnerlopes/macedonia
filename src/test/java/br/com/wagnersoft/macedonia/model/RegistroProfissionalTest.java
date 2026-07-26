package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistroProfissionalTest {

  private RegistroProfissional rp;

  @BeforeEach
  void setUp() {
    rp = new RegistroProfissional();
    rp.setId(1);
    rp.setConselho("C");
    rp.setNumero("1");
    rp.setUf("UF");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    // Assert
    assertEquals(1, rp.getId());
    assertEquals("C", rp.getConselho());
    assertEquals("1", rp.getNumero());
    assertEquals("UF", rp.getUf());

  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {

    // Arrange
    RegistroProfissional igual = new RegistroProfissional();
    igual.setId(1);

    RegistroProfissional diferente = new RegistroProfissional();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(rp.equals(rp));
    assertTrue(rp.equals(igual));
    assertEquals(rp.hashCode(), igual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertFalse(rp.equals(diferente));
    assertNotEquals(rp.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(rp.equals(null));
    assertNotEquals("Uma String qualquer", rp);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = rp.toString();
    assertTrue(toStringResult.contains("C-UF 1"));
  }

}
