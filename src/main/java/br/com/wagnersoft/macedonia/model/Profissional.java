package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Profissional implements Comparable<Profissional>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;

	private String nome;

	private String crm;

	@Column(name="crm_uf")
	private String crmUf;

	@ManyToOne
	@JoinColumn(name="especialidade_codigo")
	private Especialidade especialidade;

	@Override
	public int compareTo(Profissional o) {
		return this.getNome().compareTo(o.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Profissional other = (Profissional) obj;
		return Objects.equals(cpf, other.cpf);
	}

	public String getCrmDotUf() {
		final DecimalFormat df = new DecimalFormat();
		df.setGroupingUsed(true);
		return "CRM" + "-" + this.getCrmUf() + " " + df.format(Long.valueOf(this.getCrm()));
	}

}
