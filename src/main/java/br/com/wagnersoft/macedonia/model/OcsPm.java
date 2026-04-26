package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="ocs_pm")
@NamedQuery(name="OcsPm.findByPM", query="SELECT o FROM OcsPm o WHERE o.pm.id = ?1")
@NamedQuery(name="OcsPm.findByOCS", query="SELECT o FROM OcsPm o WHERE o.ocs.id = ?1")
@NamedQuery(name="OcsPm.excluirPorOcs", query="DELETE FROM OcsPm o WHERE o.ocs.id = ?1")
public class OcsPm implements Comparable<OcsPm>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="ch_qtd")
	private Integer chQtd;

	@Column(name="valor")
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@ManyToOne
	@JoinColumn(name="pm_id")
	private ProcedimentoMedico pm;

	public OcsPm(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(OcsPm o) {
		return this.pm.getDescricao().compareTo(o.pm.getDescricao());
	}

}