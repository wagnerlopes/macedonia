package br.com.wagnersoft.macedonia.service;

import java.util.Collections;
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

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.repository.BeneficiarioRepository;

/**
 * Serviço responsável pelo gerenciamento e pelas regras de negócio da entidade {@link Beneficiario}.
 * <p>
 * Centraliza as operações de cadastro, atualização, consulta e validações 
 * de domínio dos beneficiários no sistema.
 * </p>
 *
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class BeneficiarioService {

  private static final Logger logger = LoggerFactory.getLogger(BeneficiarioService.class);

  @Autowired
  private BeneficiarioRepository rep;

  public Optional<Beneficiario> findByCpf(final String cpf) {
    if (cpf == null || cpf.isBlank()) return Optional.empty();
    return rep.findById(cpf.replaceAll("[^0-9]", ""));
  }

  public List<Beneficiario> findByNome(final String nome) {
    if (nome == null || nome.isBlank()) return Collections.emptyList();
    return rep.findByNome(nome);
  }

  public List<Beneficiario> listAll() {
    final List<Beneficiario> lista = rep.findAll(Sort.by(Sort.Order.by("nome").ignoreCase()));
    logger.debug("{}", lista);
    return lista;  }
  /** Retorna apenas o mapa de nome (value) e o cpf (key) usado em select list.
   * @return {@link Map<String, String>} CPF e Nome
   */
  public Map<String, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(Beneficiario::getCpf, Beneficiario::getNome, (existing, replacement) -> existing, LinkedHashMap::new));
  }

  public void remove(final String cpf) {
    if (cpf == null || cpf.isBlank()) return;
    rep.findById(cpf.replaceAll("[^0-9]","")).ifPresent(b -> rep.delete(b));
  }

  public void add(final Beneficiario beneficiario) {
    if (beneficiario == null) return;
    Optional.ofNullable(beneficiario.getCpf())
        .flatMap(rep::findById)
        .ifPresentOrElse(existing -> this.save(existing, beneficiario), () -> rep.save(beneficiario));
  }

  private void save(final Beneficiario existing, final Beneficiario replacement) {
    existing.setNome(replacement.getNome());
    existing.setNascimentoData(replacement.getNascimentoData());
    rep.save(existing);
  }

}
