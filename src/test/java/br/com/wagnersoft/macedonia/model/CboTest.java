package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CboTest {

  Cbo cbo = new Cbo();

  @BeforeEach
  void setUp() {
    cbo.setCodigo("1234");
    cbo.setDescricao("XXXX");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    assertEquals("1234", cbo.getCodigo());
    assertEquals("XXXX", cbo.getDescricao());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no codigo")
  void testEqualsAndHashCode() {

    // Arrange
    Cbo igual = new Cbo();
    igual.setCodigo("1234");
    igual.setDescricao("XXXX");

    Cbo diferente = new Cbo();
    diferente.setCodigo("1");
    diferente.setDescricao("X");

    // Teste de igualdade (mesmo codigo)
    assertTrue(cbo.equals(cbo));
    assertTrue(cbo.equals(igual));
    assertEquals(cbo.hashCode(), igual.hashCode());

    // Teste de diferença (codigos diferentes)
    assertFalse(cbo.equals(diferente));
    assertNotEquals(cbo.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(cbo.equals(null));
    assertNotEquals(cbo, "Uma String qualquer");

  }

}
