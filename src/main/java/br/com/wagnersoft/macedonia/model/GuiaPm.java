package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

/** GuiaPm Entity.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="guia_pm")
@ToString
public class GuiaPm implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="pm_qtd")
	private Integer pmQtd;

	@Column(name="unidade_medida")
	private String unidadeMedida;

	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="valor_unitario")
	private BigDecimal valorUnitario;

	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="valor_total")
	private BigDecimal valorTotal;
	
	@NumberFormat(style = NumberFormat.Style.CURRENCY)
	@Column(name="pos_auditoria")
	private BigDecimal posAuditoria;

	@Exclude
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name="pm_id", updatable = false, nullable = false)
	private ProcedimentoMedico pm;

	@Exclude
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="guia_id", updatable = false, nullable = false)
	private GuiaEncaminhamento guiaEncaminhamento;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GuiaPm other = (GuiaPm) obj;
		return Objects.equals(id, other.id);
	}
	
}
