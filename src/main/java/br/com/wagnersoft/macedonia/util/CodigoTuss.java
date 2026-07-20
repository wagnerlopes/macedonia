package br.com.wagnersoft.macedonia.util;

import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  Terminologia Unificada da Saúde Suplementar (TUSS). 
 *  TODO: refatorar
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public final class CodigoTuss {

  private String codigo;

  private String grupo;

  private String subgrupo;

  private String sequencial;

  private String dv;

  public CodigoTuss(String x) {
    if (x.length() != 8) {
      throw new IllegalArgumentException();
    }
    this.grupo = x.substring(0,2);
    this.subgrupo = x.substring(2,4);
    this.sequencial = x.substring(4,7);
    this.dv = x.substring(7);
  }

  @Override
  public String toString() {
    return grupo + "." + subgrupo + "." + sequencial + "-" + dv;
  }

  public String getCodigoTuss() {
    return codigo.isEmpty() ? "COD TUSS" :
      codigo.substring(0,2) + "." +
      codigo.substring(2,4) + "." +
      codigo.substring(4,7) + "-" +
      codigo.substring(7);
  }

  public static boolean isValid(final String codigo) {
    boolean status = false;
    if (codigo != null && codigo.length() == 8) {
      status = modulo10(codigo) == 0;
    }
    return status;
  }

  public static int modulo10(final String codigo) {
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

  public static void main(String[] args) {
    CodigoTuss tuss = new CodigoTuss("10102019");
    System.out.println("TUSS = " + tuss.toString());
    System.out.println(CodigoTuss.isValid(tuss.codigo + tuss.subgrupo + tuss.sequencial + tuss.dv));
    System.out.println(CodigoTuss.builder().grupo("10").subgrupo("10").sequencial("101").dv("2").build());
    System.out.println(CodigoTuss.isValid("10101012"));
    System.out.println(CodigoTuss.modulo10("1010101"));
  }

}
