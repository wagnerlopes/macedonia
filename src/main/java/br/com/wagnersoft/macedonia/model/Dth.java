package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.TableGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the dth database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@NamedQueries(value={
		@NamedQuery(name="Dth.findByDesc", query="SELECT o FROM Dth o WHERE UPPER(o.descricao) LIKE ?1"),
		@NamedQuery(name="Dth.findByOCS", query="SELECT o FROM Dth o WHERE o.ocs.id = ?1"),
		@NamedQuery(name="Dth.excluirPorOcs", query="DELETE FROM Dth o WHERE o.ocs.id = ?1")
})
public class Dth implements Comparable<Dth>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TG_DTH")
	@TableGenerator(name="TG_DTH", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="DTH", allocationSize=1)
	private Integer id;

	private String codigo;

	private String descricao;

	private String unidade;

	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name="ocs_id", updatable=false, nullable=false)
	private Ocs ocs;

	@Override
	public int compareTo(Dth o) {
		return this.getCodigo().compareTo(o.getCodigo());
	}

/*
	public String getCodigof() {
		return this.codigo.isEmpty() || this.codigo.length() < 8 ? "CODIGO" : new StringBuilder(codigo.substring(0,2)).append(".")
				.append(codigo.substring(2,4)).append(".")
				.append(codigo.substring(4,7)).append("-")
				.append(codigo.substring(7)).toString();
	}
*/
}
