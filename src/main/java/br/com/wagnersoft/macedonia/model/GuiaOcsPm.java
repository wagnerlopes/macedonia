package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="guia_ocs_pm")
public class GuiaOcsPm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="pm_qtd")
	private Integer pmQtd;

	@Column(name="pos_auditoria")
	private BigDecimal posAuditoria;

	@ManyToOne
	@JoinColumn(name="guia_id")
	private GuiaEncaminhamento guiaEncaminhamento;

	@ManyToOne
	@JoinColumn(name="ocs_pm_id")
	private OcsPm ocsPm;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GuiaOcsPm other = (GuiaOcsPm) obj;
		return Objects.equals(id, other.id);
	}
	
}
