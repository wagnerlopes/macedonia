package br.com.wagnersoft.macedonia.type;

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
		CodigoTuss tuss = new CodigoTuss("10102019");
		System.out.println("TUSS = " + tuss.toString());
		System.out.println(CodigoTuss.isValid(tuss.codigo + tuss.subgrupo + tuss.sequencial + tuss.dv));
		System.out.println(CodigoTuss.builder().grupo("10").subgrupo("10").sequencial("101").dv("2").build());
		System.out.println(CodigoTuss.isValid("10101012"));
		System.out.println(CodigoTuss.modulo10("1010101"));
	}

}
