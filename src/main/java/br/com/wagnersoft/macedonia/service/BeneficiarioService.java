package br.com.wagnersoft.macedonia.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.repository.BeneficiarioRepository;

/** Beneficiario service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class BeneficiarioService {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiarioService.class);

	@Autowired
	private BeneficiarioRepository rep;

	public Optional<Beneficiario> findByCpf(String cpf) {
		return rep.findById(cpf);
	}
	
	public List<Beneficiario> listAll() {
		final List<Beneficiario> lista = rep.findAll();
		lista.forEach(e -> {logger.debug(e.toString());});
		return lista;    }
	/** Usado nas listas onde interessa apenas o nome (value) e o cpf (key).
	 * @return
	 */
	public Map<String, String> mapAll() {
		return listAll().stream()
		    .sorted(Comparator.comparing(Beneficiario::getNome, String.CASE_INSENSITIVE_ORDER))
		    .collect(Collectors.toMap(Beneficiario::getCpf, Beneficiario::getNome, (existing, replacement) -> existing, LinkedHashMap::new));
	}

	public void remove(final String cpf) {
		 rep.findById(cpf).ifPresent(b -> rep.delete(b));
	}
	
	public void add(final Beneficiario beneficiario) {
	  if (beneficiario == null) return;
	  Optional.ofNullable(beneficiario.getCpf())
      .flatMap(rep::findById)
      .ifPresentOrElse(existing -> this.save(existing, beneficiario), () -> rep.save(beneficiario));
	}

	private void save(final Beneficiario existing, final Beneficiario newBen) {
		existing.setNome(newBen.getNome());
		existing.setNascimentoData(newBen.getNascimentoData());
		rep.save(existing);
	}
	
}
