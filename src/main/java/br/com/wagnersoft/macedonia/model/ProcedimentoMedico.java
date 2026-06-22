package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.wagnersoft.macedonia.type.CodigoTuss;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Include;

/** ProcedimentoMedico Entity.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="procedimento_medico")
@ToString(onlyExplicitlyIncluded = true)
public class ProcedimentoMedico implements Comparable<ProcedimentoMedico>, Serializable {

	private static final long serialVersionUID = 1L;

	@Include
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String amb90;

	private String amb92;

	private String amb96;

	private String amb99;

	@Include
	private String tuss;
	
	private String grupo;

	private String subgrupo;

	@Include
	private String descricao;

	@Column(name="auxiliares_qtd")
	private int auxiliaresQtd;

	@Column(name="ch_qtd")
	private int chQtd;

	@Column(name="porte_anestesico")
	private int porteAnestesico;

	@Override
	public int compareTo(ProcedimentoMedico o) {
		return this.getDescricao().compareTo(o.getDescricao());
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
		ProcedimentoMedico other = (ProcedimentoMedico) obj;
		return Objects.equals(id, other.id);
	}

	public String getCodigoTuss() {
		return new CodigoTuss(tuss).getCodigoTuss();
	}
	
}
