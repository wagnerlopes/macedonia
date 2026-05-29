package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.NumberFormat;

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
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="guia_encaminhamento")
public class GuiaEncaminhamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@FutureOrPresent
	@Column(name="emissao_data", updatable = false)
	private LocalDate emissaoData;

	@NonNull
	@Column(name="guia_nr", updatable = false)
	private Integer guiaNr;

	@Column(name="operador")
	private String operador;

	@Column(name="observacao")
	private String observacao;

	@Exclude
	@ManyToOne
	@JoinColumn(name="solicitante_cpf")
	private Profissional solicitante;

	@Exclude
	@ManyToOne
	@JoinColumn(name="responsavel_cpf")
	private Profissional responsavel;

	@NonNull
	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="valor_total")
	private BigDecimal valorTotal;

	@NonNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="beneficiario_cpf", updatable = false)
	private Beneficiario beneficiario;

	@NonNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="protocolo_id")
	private Protocolo protocolo;

	@Exclude
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
