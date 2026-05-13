package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Beneficiario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;

	@NonNull
	private String nome;

	@NonNull
	@Column(name="nascimento_data")
	private LocalDate nascimentoData;

	@OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<GuiaEncaminhamento> guias = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beneficiario other = (Beneficiario) obj;
		return Objects.equals(cpf, other.cpf);
	}

	public long getIdade() {
	    if (nascimentoData == null) return 0;
	    final LocalDate today = LocalDate.now();
	    if (nascimentoData.isAfter(today)) return -1;
	    return ChronoUnit.YEARS.between(nascimentoData, today);
	}	

	public String getFaixaEtaria() {
		long idade = getIdade();
		long s = idade == 0 ? idade + 1 : idade%10 == 0 ? idade - 1 : idade;
		return Math.round(s / 10) * 10 + " a " + Math.round((s + 9) / 10) * 10;
	}

}
