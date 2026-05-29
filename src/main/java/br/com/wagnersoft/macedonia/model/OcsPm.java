package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="ocs_pm")
@ToString
public class OcsPm implements Comparable<OcsPm>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="ch_qtd")
	private Integer chQtd;

	@Column(name="unidade_medida")
	private String unidadeMedida;

	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pm_id")
	private ProcedimentoMedico pm;

	@Override
	public int compareTo(OcsPm o) {
		return this.pm.getDescricao().compareTo(o.pm.getDescricao());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OcsPm other = (OcsPm) obj;
		return Objects.equals(id, other.id);
	}
	
}
