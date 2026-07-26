package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DthTest {

  Dth dth;

  @BeforeEach
  void setUp() {
    dth = new Dth();
    dth.setId(1);
    dth.setCodigo("1");
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    Ocs ocs = new Ocs();
    BigDecimal valor = BigDecimal.valueOf(100.98);

    // Act
    dth.setDescricao("Teste 1");
    dth.setUnidadeMedida("Diaria");
    dth.setValorUnitario(valor);
    dth.setOcs(ocs);

    // Assert
    assertEquals(1, dth.getId());
    assertEquals("1", dth.getCodigo());
    assertEquals("Teste 1", dth.getDescricao());
    assertEquals("Diaria", dth.getUnidadeMedida());
    assertEquals(valor, dth.getValorUnitario());
    assertEquals(ocs, dth.getOcs());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {
    // Arrange
    Dth igual = new Dth();
    igual.setId(1);
    igual.setCodigo("1");

    Dth diferente = new Dth();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(dth.equals(dth));
    assertTrue(dth.equals(igual));
    assertEquals(dth.hashCode(), igual.hashCode());
    assertTrue(dth.compareTo(igual) == 0);

    // Teste de diferença (IDs diferentes)
    assertFalse(dth.equals(diferente));
    assertNotEquals(dth.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(dth.equals(null));
    assertNotEquals(dth, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    // Verifica se o toString do Lombok gera uma string contendo informações essenciais
    String toStringResult = dth.toString();
    assertTrue(toStringResult.contains("Dth"));
    assertTrue(toStringResult.contains("id=1"));
  }

  @Test
  @DisplayName("Deve formatar codigo corretamente")
  void testFormatCodigo() {
    // Arrange
    dth.setCodigo("12345678");

    // Act
    String formatCodigo = Dth.formatCodigo(dth.getCodigo());

    // Assert
    assertEquals("12.34.567-8", formatCodigo);

    // Arrange
    dth.setCodigo(null);

    // Act
    formatCodigo = Dth.formatCodigo(dth.getCodigo());

    // Assert
    assertNull(formatCodigo);

    // Arrange
    dth.setCodigo("");

    // Act
    formatCodigo = Dth.formatCodigo(dth.getCodigo());

    // Assert
    assertEquals(formatCodigo, "");
    
    // Arrange
    dth.setCodigo("1");

    // Act
    formatCodigo = Dth.formatCodigo(dth.getCodigo());

    // Assert
    assertEquals("1", formatCodigo);
    
  }

}
