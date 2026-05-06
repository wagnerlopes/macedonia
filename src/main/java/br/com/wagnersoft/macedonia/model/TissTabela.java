package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name="tiss_tabela")
public class TissTabela implements Comparable<TissTabela>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;

	private String descricao;

	@ManyToOne
	@JoinColumn(name="tipo_acomodacao_codigo")
	private TipoAcomodacao tipoAcomodacao;

	@ManyToOne
	@JoinColumn(name="tipo_atendimento_codigo")
	private TipoAtendimento tipoAtendimento;
	
	@ManyToOne
	@JoinColumn(name="tipo_consulta_codigo")
	private TipoConsulta tipoConsulta;

	@ManyToOne
	@JoinColumn(name="tipo_guia_consulta_codigo")
	private TipoGuiaConsulta tipoGuiaConsulta;

	@OneToMany(mappedBy="tissTabela")
	private List<TissProcedimento> tissProcedimentos;
	
	@Override
	public int compareTo(TissTabela o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

}

/*
public TissProcedimento addTissProcedimento(TissProcedimento tissProcedimento) {
getTissProcedimentos().add(tissProcedimento);
tissProcedimento.setTissTabela(this);
return tissProcedimento;
}

public TissProcedimento removeTissProcedimento(TissProcedimento tissProcedimento) {
getTissProcedimentos().remove(tissProcedimento);
tissProcedimento.setTissTabela(null);
return tissProcedimento;
}
*/
