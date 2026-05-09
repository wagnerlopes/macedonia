package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Diária de Tratamento Hospitalar (DTH). */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dth implements Comparable<Dth>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String codigo;

	private String descricao;

	private String unidade;

	private BigDecimal valor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@Override
	public int compareTo(Dth o) {
		return this.getCodigo().compareTo(o.getCodigo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Dth other = (Dth) obj;
		return Objects.equals(id, other.id);
	}
	
}

/*
  public String getCodigof() {
	return this.codigo.isEmpty() || this.codigo.length() < 8 ? "CODIGO" : new StringBuilder(codigo.substring(0,2)).append(".")
			.append(codigo.substring(2,4)).append(".")
			.append(codigo.substring(4,7)).append("-")
			.append(codigo.substring(7)).toString();
  }

  @GeneratedValue(strategy=GenerationType.TABLE, generator="TG_DTH")
  @TableGenerator(name="TG_DTH", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="DTH", allocationSize=1)
*/
