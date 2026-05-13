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

	public Beneficiario findByCpf(String cpf) {
		return rep.findById(cpf).orElseThrow();
	}
	
	public List<Beneficiario> listAll() {
		final List<Beneficiario> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;    }
	public void add(Beneficiario beneficiario) {
		rep.save(beneficiario);
	}

}
