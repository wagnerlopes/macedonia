package br.com.wagnersoft.macedonia.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.repository.ProfissionalRepository;

/** Profissional de Saude service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class ProfissionalService {

	private static final Logger logger = LoggerFactory.getLogger(ProfissionalService.class);

	@Autowired
	private ProfissionalRepository rep;

	public Optional<Profissional> findByCpf(final String cpf) {
    if (cpf == null || cpf.isBlank()) return Optional.empty();
    return rep.findById(cpf.replaceAll("[^0-9]", ""));
	}

	public List<Profissional> listAll() {
    final List<Profissional> lista = rep.findAll(Sort.by(Sort.Order.by("nome").ignoreCase()));
    logger.debug("{}", lista);
    return lista;
	}

  /** Retorna apenas o mapa de nome (value) e o cpf (key) usado em select list.
   * @return {@link Map<String, String>} CPF e Nome
   */
	public Map<String, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(Profissional::getCpf, Profissional::getNome, (existing, replacement) -> existing, LinkedHashMap::new));
	}
	
	public void remove(final String cpf) {
    if (cpf == null || cpf.isBlank()) return;
		rep.findById(cpf.replaceAll("[^0-9]","")).ifPresent(p -> {
			if (p.getGuiasResponsavel().isEmpty() && p.getGuiasSolicitante().isEmpty())
				rep.delete(p);
		});
	}

  public void add(final Profissional profissional) {
    if (profissional == null) return;
    Optional.ofNullable(profissional.getCpf())
      .flatMap(rep::findById)
      .ifPresentOrElse(existing -> this.save(existing, profissional), () -> rep.save(profissional));
  }

	private void save(final Profissional existing, final Profissional replacement) {
		existing.setNome(replacement.getNome());
		existing.setCbo(replacement.getCbo());
		existing.setCns(replacement.getCns());
		existing.setRegistroProfissional(replacement.getRegistroProfissional());
		rep.save(existing);
	}
	
}
