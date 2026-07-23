package br.com.wagnersoft.macedonia.util;

import java.util.stream.IntStream;

/**
 *  Utilitário de validação da fórmula de Luhn (módulo 10).
 *  
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public final class LuhnCheck {

  LuhnCheck() {
    // Protected
  }

  public static int generate(final String codigo) {
    int soma = IntStream.range(0, codigo.length())
        .map(i -> {
          int d = codigo.charAt(i) - '0';
          if (i % 2 == 0) {
            d *= 2;
            return (d > 9) ? d - 9 : d;
          } else {
            return d;
          }
        })
        .sum();

    int resto = soma % 10;
    return (resto == 0) ? 0 : 10 - resto;
  }

}
