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

	public GuiaOcsPm(final GuiaEncaminhamento guia, final OcsPm ocsPm, final Integer pmQtd) {
		this.guiaEncaminhamento = guia;
		this.ocsPm = ocsPm;
		this.pmQtd = pmQtd;
	}


}