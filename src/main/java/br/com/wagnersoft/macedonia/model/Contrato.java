package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.TableGenerator;
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
@NamedQuery(name="Contrato.findByOcs", query="SELECT c FROM Contrato c WHERE c.ocs.id = ?1")
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TGC")
	@TableGenerator(name="TGC", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="CONTRATOS", allocationSize=1)
	private Integer id;

	@Column(name="final_data")
	private LocalDate finalData;

	@Column(name="inicio_data")
	private LocalDate inicioData;

	@Column(name="ch_valor")
	private BigDecimal chValor;

	@ManyToOne
	@JoinColumn(name="tipo_termo_contrato_codigo")
	private TipoTermoContrato tipoTermoContrato;

	@ManyToOne
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

}
