package br.com.wagnersoft.macedonia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StringToBigDecimalConverterTest {

  @Test
  @DisplayName("Deve retornar BigDecimal")
  void deveRetornarBigDecimal() {
    // Arrange
    String decimal = "1.230,89";
    
    // Act
    BigDecimal result = new StringToBigDecimalConverter().convert(decimal);
    
    // Assert
    assertNotNull(result);
    assertEquals(new BigDecimal("1230.89"), result);
  }
  
}
