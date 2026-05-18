package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="guia_encaminhamento")
public class GuiaEncaminhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="emissao_data")
	private LocalDate emissaoData;

	@Column(name="guia_nr")
	private Integer guiaNr;

	@Column(name="operador")
	private String operador;

	@Column(name="observacao")
	private String observacao;

	@ManyToOne
	@JoinColumn(name="solicitante_cpf")
	private Profissional solicitante;

	@ManyToOne
	@JoinColumn(name="responsavel_cpf")
	private Profissional responsavel;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="beneficiario_cpf")
	private Beneficiario beneficiario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="protocolo_id")
	private Protocolo protocolo;

	@OneToMany(mappedBy = "guiaEncaminhamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<GuiaOcsPm> guiaOcsPm = new ArrayList<>();

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
		GuiaEncaminhamento other = (GuiaEncaminhamento) obj;
		return Objects.equals(id, other.id);
	}

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
	
}
