package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@NamedQueries(value={
		@NamedQuery(name="Beneficiario.findByPrecCp", query="SELECT b FROM Beneficiario b WHERE b.precCp = ?1"),
		@NamedQuery(name="Beneficiario.excluir", query="DELETE FROM Beneficiario b ")
})
public class Beneficiario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String codom;

	@Column(name="nascimento_data")
	private LocalDate nascimentoData;

	private String nome;

	@Column(name="prec_cp")
	private String precCp;

	@OneToMany(mappedBy="beneficiario", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<GuiaEncaminhamento> guias;



/*
	public GuiaEncaminhamento removeGuia(GuiaEncaminhamento guia) {
		this.getGuias().remove(guia);
		guia.setBeneficiario(null);
		return guia;
	}

	public Integer getIdade() {
		if (this.getNascimentoData() == null) {
			throw new IllegalArgumentException("Beneficiario nao tem data de nascimento cadastrada");
		}
		final Calendar hoje = new GregorianCalendar();
		final Calendar dn = new GregorianCalendar();
		dn.setTime(this.getNascimentoData());
		int idade = hoje.get(Calendar.YEAR) - dn.get(Calendar.YEAR);
		boolean isMesMaior = dn.get(Calendar.MONTH) >= hoje.get(Calendar.MONTH);
		boolean isMesIgualMasDiaMaior = dn.get(Calendar.MONTH) == hoje.get(Calendar.MONTH) && dn.get(Calendar.DAY_OF_MONTH) > hoje.get(Calendar.DAY_OF_MONTH);
		if (isMesMaior || isMesIgualMasDiaMaior) {
			idade -= 1;
		}
		return idade;
	}

	public String getFaixaEtaria() {
		String aux = "101 a 110";
		if (this.getIdade() < 11) {
			aux = "0 a 10";
		} else if (this.getIdade() < 21) {
			aux = "11 a 20";
		} else if (this.getIdade() < 31) {
			aux = "21 a 30";
		} else if (this.getIdade() < 41) {
			aux = "31 a 40";
		} else if (this.getIdade() < 51) {
			aux = "41 a 50";
		} else if (this.getIdade() < 61) {
			aux = "51 a 60";
		} else if (this.getIdade() < 71) {
			aux = "61 a 70";
		} else if (this.getIdade() < 81) {
			aux = "71 a 80";
		} else if (this.getIdade() < 91) {
			aux = "81 a 90";
		} else if (this.getIdade() < 101) {
			aux = "91 a 100";
		}
		return aux;
	}
*/
	
}
