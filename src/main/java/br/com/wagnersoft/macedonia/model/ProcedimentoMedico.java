package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="procedimento_medico")
public class ProcedimentoMedico implements Comparable<ProcedimentoMedico>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String amb90;

	private String amb92;

	private String amb96;

	private String amb99;

	@Column(name="auxiliares_qtd")
	private int auxiliaresQtd;

	@Column(name="ch_qtd")
	private int chQtd;

	@Column(name="porte_anestesico")
	private int porteAnestesico;

	private String descricao;

	private String grupo;

	private String subgrupo;

	private String tuss;

	@Override
	public int compareTo(ProcedimentoMedico o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

	public String getTussf() {
		return this.tuss.isEmpty() ? "CNPJ" : new StringBuilder(tuss.substring(0,2)).append(".")
				.append(tuss.substring(2,4)).append(".")
				.append(tuss.substring(4,7)).append("-")
				.append(tuss.substring(7)).toString();
	}

}
