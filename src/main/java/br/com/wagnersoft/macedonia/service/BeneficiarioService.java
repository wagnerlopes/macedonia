package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.repository.BeneficiarioRepository;

@Service
public class BeneficiarioService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiarioService.class);

	@Autowired
	private BeneficiarioRepository rep;

	public List<Beneficiario> listar() {
		final List<Beneficiario> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}

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
