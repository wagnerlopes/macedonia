package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.repository.ContratoRepository;

/** Contrato com Estabelecimento de Saude service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class ContratoService {

  private static final Logger logger = LoggerFactory.getLogger(ContratoService.class);

  @Autowired
  private ContratoRepository rep;

  public Optional<Contrato> findById(Integer id) {
    if (id == null) return Optional.empty();
    return rep.findById(id);
  }

  public List<Contrato> listAll() {
    final List<Contrato> lista = rep.findAll(Sort.by(Sort.Order.by("id")));
    logger.debug("{}", lista);
    return lista;
  }

  public void remove(final Integer id) {
    if (id == null) return;
    rep.findById(id).ifPresent(c -> rep.delete(c));
  }

  public void add(Contrato contrato) {
    if (contrato == null) return;
    Optional.ofNullable(contrato.getId())
        .flatMap(rep::findById)
        .ifPresentOrElse(existing -> this.save(existing, contrato), () -> rep.save(contrato));
  }

  private void save(final Contrato existing, final Contrato replacement) {
    logger.debug("existing: {}", existing);
    logger.debug("replacement: {}", replacement);
    existing.setChQtd(replacement.getChQtd());
    existing.setInicioData(replacement.getInicioData());
    existing.setTerminoData(replacement.getTerminoData());
    existing.setOcs(replacement.getOcs());
    rep.save(existing);
  }

}
