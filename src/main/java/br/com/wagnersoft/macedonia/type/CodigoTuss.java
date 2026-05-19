package br.com.wagnersoft.macedonia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Terminologia Unificada da Saúde Suplementar (TUSS). */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class Tuss {

	private String grupo;

	private String subgrupo;

	private String sequencial;

	private String dv;

	@Override
	public String toString() {
	  return grupo + "." + subgrupo + "." + sequencial + "-" + dv;
	}
	
	public static boolean isValid(final String codigo) {
		boolean status = false;
		if (codigo != null && codigo.length() == 8) {
			status = modulo10(codigo) == 0;
		}
		return status;
	}

	public static int modulo10(final String codigo) {
		int soma = 0;
		final char[] c = codigo.toCharArray();
		for (int i=0; i < c.length; i++) {
			int d = Character.digit(c[i], 10);
			if (i%2 == 0) {
				d *= 2;
				if (d > 9) {
					soma += Integer.valueOf(Integer.toString(d).substring(0,1)) +
							Integer.valueOf(Integer.toString(d).substring(1)); 
				} else {
					soma += d;
				}
			} else {
				soma += d;
			}
		}
		return soma%10 == 0 ? 0 : 10 - soma%10;
	}
	
  public static void main(String[] args) {
    System.out.println(Tuss.builder().grupo("10").subgrupo("10").sequencial("101").dv("2").build());
    System.out.println(Tuss.isValid("10101012"));
    System.out.println(Tuss.modulo10("1010101"));
  }

}
