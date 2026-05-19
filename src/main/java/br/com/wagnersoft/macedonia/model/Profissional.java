package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	private String nome;

	@ManyToOne
	@JoinColumn(name="cbo_codigo")
	private CBO cbo;

	private String cns;
	
	@Id
	private String cpf;

	@OneToOne
	@JoinColumn(name="registro_id")
	private RegistroProfissional registroProfissional;

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

}
