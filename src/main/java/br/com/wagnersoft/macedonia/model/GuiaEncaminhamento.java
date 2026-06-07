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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	@NotNull
	@Column(name="emissao_data", updatable = false)
	private LocalDate emissaoData;

	@NotNull
	@Column(name="guia_nr", updatable = false)
	private Integer guiaNr;

	@Column(name="operador")
	private String operador;

	@Column(name="observacao")
	private String observacao;

	@NotNull
	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="valor_total")
	private BigDecimal valorTotal;

	@NotNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="solicitante_cpf", updatable = false, nullable = false)
	private Profissional solicitante;

	@NotNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="responsavel_cpf", updatable = false, nullable = false)
	private Profissional responsavel;

	@NotNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="beneficiario_cpf", updatable = false, nullable = false)
	private Beneficiario beneficiario;

	@NotNull
	@Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="ocs_id", updatable = false, nullable = false)
	private Ocs ocs;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="protocolo_id")
	private Protocolo protocolo;

	@Exclude
	@OneToMany(mappedBy = "guiaEncaminhamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<GuiaPm> procedimentos = new ArrayList<>();

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

	public GuiaPm addGuiaPm(GuiaPm gpm) {
		if (this.getProcedimentos().contains(gpm)) {
			this.getProcedimentos().remove(gpm);
		}
		this.getProcedimentos().add(gpm);
		gpm.setGuiaEncaminhamento(this);
		return gpm;
	}

	public GuiaPm removeGuiaPm(GuiaPm gpm) {
		this.getProcedimentos().remove(gpm);
		gpm.setGuiaEncaminhamento(null);
		return gpm;
	}
	
}
