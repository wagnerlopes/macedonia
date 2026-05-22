package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Profissional implements Comparable<Profissional>, Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name="cbo_codigo")
	private Cbo cbo;

	private String cns;
	
	@Id
	private String cpf;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="registro_id")
	private RegistroProfissional registroProfissional;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "solicitante", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GuiaEncaminhamento> guiasSolicitante = new ArrayList<>();

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "responsavel", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GuiaEncaminhamento> guiasResponsavel = new ArrayList<>();

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
