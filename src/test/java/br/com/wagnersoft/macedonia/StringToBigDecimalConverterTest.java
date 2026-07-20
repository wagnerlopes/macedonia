package br.com.wagnersoft.macedonia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.wagnersoft.macedonia.config.StringToBigDecimalConverter;

public class StringToBigDecimalConverterTest {

  // Instância única para evitar repetição de 'new'
  private final StringToBigDecimalConverter converter = new StringToBigDecimalConverter();

  @Test
  @DisplayName("String x.xxx,xx Deve retornar BigDecimal")
  void pontoVirgulaDeveRetornarBigDecimal() {
    // Arrange
    String decimal = "1.230,89";
    
    // Act
    BigDecimal result = converter.convert(decimal);
    
    // Assert
    assertNotNull(result);
    assertEquals(new BigDecimal("1230.89"), result);
  }

  @Test
  @DisplayName("String xxx,xx Deve retornar BigDecimal")
  void virgulaDeveRetornarBigDecimal() {
    // Arrange
    String decimal = "1230,89";
    
    // Act
    BigDecimal result = converter.convert(decimal);
    
    // Assert
    assertNotNull(result);
    assertEquals(new BigDecimal("1230.89"), result);
  }

  @Test
  @DisplayName("String ABCD Deve retornar NULL")
  void stringDeveRetornarNull() {
    // Arrange
    String decimal = "ABCD";
    
    // Act
    BigDecimal result = converter.convert(decimal);
    
    // Assert
    assertNull(result);
  }

  @Test
  @DisplayName("String NULL Deve retornar NULL")
  void nuloDeveRetornarNull() {
    // Arrange
    String decimal = null;
    
    // Act
    BigDecimal result = converter.convert(decimal);
    
    // Assert
    assertNull(result);
  }

  @Test
  @DisplayName("String vazia Deve retornar NULL")
  void vazioRetornarNull() {
    // Arrange
    String decimal = "";
    
    // Act
    BigDecimal result = converter.convert(decimal);
    
    // Assert
    assertNull(result);
  }
}
