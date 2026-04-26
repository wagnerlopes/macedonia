package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.text.DecimalFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
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
@Table(name="profissionais")
@NamedQuery(name="Profissional.findByCrm", query="SELECT p FROM Profissional p WHERE p.crm = ?1")
@NamedQuery(name="Profissional.excluir", query="DELETE FROM Profissional p")
public class Profissional implements Comparable<Profissional>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String crm;

	@Column(name="crm_uf")
	private String crmUf;

	@ManyToOne
	@JoinColumn(name="especialidade")
	private Especialidade especialidade;

	private String idt;

	private String nome;

	@Override
	public int compareTo(Profissional o) {
		return this.getNome().compareTo(o.getNome());
	}

	public String getCrmf() {
		final DecimalFormat df = new DecimalFormat();
		df.setGroupingUsed(true);
		return new StringBuilder("CRM").append("-").append(this.getCrmUf()).append(" ").append(df.format(Long.valueOf(this.getCrm()))).toString() ;
	}

}