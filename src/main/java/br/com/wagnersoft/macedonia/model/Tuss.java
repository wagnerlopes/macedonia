package br.com.wagnersoft.macedonia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class Tuss {

	private String grupo;

	private String subgrupo;

	private String sequencial;

	private String dv;

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

  /*
  public static void main(String[] args) {
    System.out.println(new Tuss.Builder().grupo(10).subgrupo(10).sequencial(102).build());
    try {
      System.out.println(new Tuss("10101012"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  */
}

/*
public Tuss(final String codigo) {
  final Tuss tuss = new Tuss.TussBuilder()
                        .grupo(Integer.parseInt(codigo.substring(0, 2)))
                        .subgrupo(Integer.parseInt(codigo.substring(2, 4)))
                        .sequencial(Integer.parseInt(codigo.substring(4, 7))).build();
  if (!codigo.substring(7).equals(tuss.dv)) {
    throw new IllegalArgumentException("DV inv�lido");
  } else {
    this.grupo = tuss.grupo;
    this.subgrupo = tuss.subgrupo;
    this.sequencial = tuss.sequencial;
    this.dv = tuss.dv;
  }

}

@Override
public String toString() {
  return new StringBuilder(grupo).append(".").append(this.subgrupo).append(".").append(this.sequencial).append("-").append(this.dv).toString();
}

public static class Builder {

  private Tuss _temp;

  final DecimalFormat df = new DecimalFormat();

  public Builder() {
    this._temp = new Tuss();
  }

  public Builder grupo(int id){
    this.df.applyPattern("00");
    this._temp.grupo = df.format(id);
    return this;
  }

  public Builder subgrupo(int id){
    this.df.applyPattern("00");
    this._temp.subgrupo = df.format(id);
    return this;
  }

  public Builder sequencial(int id){
    this.df.applyPattern("000");
    this._temp.sequencial = df.format(id);
    return this;
  }

  public Tuss build(){
    if(StringUtils.isEmpty(_temp.grupo)||StringUtils.isEmpty(_temp.subgrupo)||StringUtils.isEmpty(_temp.sequencial)){
      throw new IllegalArgumentException("TUSS � composto de GRUPO/SUBGRUPO/SEQUENCIAL");
    }
    String aux = _temp.grupo + _temp.subgrupo + _temp.sequencial;
    _temp.dv = Integer.toString(modulo10(aux));
    aux += _temp.dv;
    return _temp;
  }

}
 */

