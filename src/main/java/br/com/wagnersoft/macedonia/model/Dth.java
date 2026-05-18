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
import lombok.ToString;

/** Diárias e Taxas Hospitalares (DTH). */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dth implements Comparable<Dth>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String codigo;

	private String descricao;

	/** Diária, Hora, Procedimento, Sessão, Uso */
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
	
    public String getDotCodigo() {
        return this.codigo.isEmpty() || this.codigo.length() < 8 ? "CODIGO" :
          codigo.substring(0,2) + "." +
          codigo.substring(2,4) + "." +
          codigo.substring(4,7) + "-" +
          codigo.substring(7);
    }

}

/*
  @GeneratedValue(strategy=GenerationType.TABLE, generator="TG_DTH")
  @TableGenerator(name="TG_DTH", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="DTH", allocationSize=1)
*/
