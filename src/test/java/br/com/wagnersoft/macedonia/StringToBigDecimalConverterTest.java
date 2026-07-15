package br.com.wagnersoft.macedonia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StringToBigDecimalConverterTest {

  @Test
  @DisplayName("String x.xxx,xx Deve retornar BigDecimal")
  void pontoVirguladeveRetornarBigDecimal() {
    // Arrange
    String decimal = "1.230,89";
    
    // Act
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    
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
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    
    // Assert
    assertNotNull(result);
    assertEquals(new BigDecimal("1230.89"), result);
  }

  @Test
  @DisplayName("String ABCD Deve retornar NULL")
  void stringDeveRetornarNull() {
    // Arrange
    String decimal = "ABCD";
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    assertNull(result);
  }

  @Test
  @DisplayName("String NULL Deve retornar NULL")
  void nuloDeveRetornarNull() {
    // Arrange
    String decimal = null;
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    assertNull(result);
  }

  @Test
  @DisplayName("String vazia Deve retornar NULL")
  void vazioRetornarNull() {
    // Arrange
    String decimal = "";
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    assertNull(result);
  }
  
}
