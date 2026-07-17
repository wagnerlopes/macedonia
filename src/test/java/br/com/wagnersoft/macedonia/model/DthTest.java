package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DthTest {

  @Test
  void testGettersAndSetters() {
    Dth Dth = new Dth();
    Ocs ocs = new Ocs();
    BigDecimal valor = BigDecimal.valueOf(100.98);
    
    Dth.setId(1);
    Dth.setCodigo("1");
    Dth.setDescricao("Teste 1");
    Dth.setUnidadeMedida("Diaria");
    Dth.setValorUnitario(valor);
    Dth.setOcs(ocs);

    assertEquals(1, Dth.getId());
    assertEquals("1", Dth.getCodigo());
    assertEquals("Teste 1", Dth.getDescricao());
    assertEquals("Diaria", Dth.getUnidadeMedida());
    assertEquals(valor, Dth.getValorUnitario());
    assertEquals(ocs, Dth.getOcs());
  }

  @Test
  void testEqualsAndHashCode() {
    // Arrange
    Dth dth1 = new Dth();
    dth1.setId(1);

    Dth dth2 = new Dth();
    dth2.setId(1);

    Dth dthDiferente = new Dth();
    dthDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(dth1, dth1);
    assertEquals(dth1, dth2);
    assertEquals(dth1.hashCode(), dth2.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(dth1, dthDiferente);
    assertNotEquals(dth1.hashCode(), dthDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, dth1);
    assertNotEquals("Uma String qualquer", dth1);
  }

  @Test
  void testToString() {
    Dth dth = new Dth();
    dth.setId(5);

    // Verifica se o toString do Lombok gera uma string contendo informações essenciais
    String toStringResult = dth.toString();
    assertTrue(toStringResult.contains("Dth"));
    assertTrue(toStringResult.contains("id=5"));
  }

  @Test
  void testFormatCodigo() {
    // Arreange
    Dth dth = new Dth();
    dth.setId(1);
    dth.setCodigo("12345678");

    // Act
    String formatCodigo = Dth.formatCodigo(dth.getCodigo());
    
    // Assert
    assertEquals("12.34.567-8", formatCodigo);
  }
  
}
