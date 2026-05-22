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
	public void remove(final String cpf) {
		 rep.findById(cpf).ifPresent(b -> rep.delete(b));
	}
	
	public void add(final Beneficiario beneficiario) {
		rep.findById(beneficiario.getCpf()).ifPresentOrElse(oldBen -> save(oldBen, beneficiario), () -> rep.save(beneficiario));
	}

	private void save(final Beneficiario oldBen, final Beneficiario newBen) {
		oldBen.setNome(newBen.getNome());
		oldBen.setNascimentoData(newBen.getNascimentoData());
		rep.save(oldBen);
	}
	
}
