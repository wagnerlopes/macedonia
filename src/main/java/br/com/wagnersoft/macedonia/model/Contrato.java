package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="final_data")
	private LocalDate finalData;

	@Column(name="inicio_data")
	private LocalDate inicioData;

	@Column(name="ch_valor")
	private BigDecimal chValor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Contrato other = (Contrato) obj;
		return Objects.equals(id, other.id);
	}
	
}

/*

@GeneratedValue(strategy=GenerationType.TABLE, generator="TGC")
@TableGenerator(name="TGC", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="CONTRATOS", allocationSize=1)

*/
