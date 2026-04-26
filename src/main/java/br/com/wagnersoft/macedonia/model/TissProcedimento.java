package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
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
@Table(name="tiss_procedimentos")
public class TissProcedimento implements Comparable<TissProcedimento>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String codigo;

	private String descricao;

	@ManyToOne
	@JoinColumn(name="tiss_tabela_codigo")
	private TissTabela tissTabela;

	@Override
	public int compareTo(TissProcedimento o) {
		return this.descricao.compareTo(o.getDescricao());
	}

}