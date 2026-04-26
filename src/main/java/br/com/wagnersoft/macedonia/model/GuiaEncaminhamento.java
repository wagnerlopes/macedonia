package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="guia_encaminhamento")
public class GuiaEncaminhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="emissao_data")
	private LocalDate emissaoData;

	@ManyToOne
	@JoinColumn(name="beneficiario_id")
	private Beneficiario beneficiario;

	@ManyToOne
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@Column(name="guia_nr")
	private Integer guiaNr;

	@Column(name="operador")
	private String operador;

	@Column(name="observacao")
	private String observacao;

	@ManyToOne
	@JoinColumn(name="solicitante_id")
	private Profissional solicitante;

	@ManyToOne
	@JoinColumn(name="responsavel_id")
	private Profissional responsavel;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	@ManyToOne
	@JoinColumn(name="protocolo_id")
	private Protocolo protocolo;

	@OneToMany(mappedBy="guiaEncaminhamento", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<GuiaOcsPm> guiaOcsPm;

	public GuiaEncaminhamento(final Beneficiario beneficiario, final Ocs ocs) {
		this();
		this.beneficiario = beneficiario;
		this.ocs = ocs;
	}


	/*
	public GuiaOcsPm addGuiaOcsPm(GuiaOcsPm guiaOcsPm) {
	  if (!this.getGuiaOcsPm().contains(guiaOcsPm)) {
	    this.getGuiaOcsPm().add(guiaOcsPm);
	    guiaOcsPm.setGuiaEncaminhamento(this);
	  }
		return guiaOcsPm;
	}

	public GuiaOcsPm removeGuiaOcsPm(GuiaOcsPm guiaOcsPm) {
		getGuiaOcsPm().remove(guiaOcsPm);
		guiaOcsPm.setGuiaEncaminhamento(null);
		return guiaOcsPm;
	}
	 */

}