package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.repository.DthRepository;

/** Diarias e Taxas (DTH) Service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class DthService {

  private static final Logger logger = LoggerFactory.getLogger(DthService.class);

  @Autowired
  private DthRepository rep;

  public Optional<Dth> findById(final Integer id) {
    if (id == null) return Optional.empty();
    return rep.findById(id);
  }

  public List<Dth> listAll() {
    final List<Dth> lista = rep.findAll(Sort.by(Sort.Order.by("id")));
    logger.debug("{}", lista);
    return lista;
  }

  public void remove(final Integer id) {
    if (id == null) return;
    rep.findById(id).ifPresent(c -> rep.delete(c));
  }

  public void add(Dth dth) {
    if (dth == null) return;
    Optional.ofNullable(dth.getId())
        .flatMap(rep::findById)
        .ifPresentOrElse(existing -> this.save(existing, dth), () -> rep.save(dth));
  }

  private void save(final Dth existing, final Dth replacement) {
    logger.debug("existing: {}", existing);
    logger.debug("replacement: {}", replacement);
    existing.setDescricao(replacement.getDescricao());
    existing.setCodigo(replacement.getCodigo());
    existing.setUnidadeMedida(replacement.getUnidadeMedida());
    existing.setValorUnitario(replacement.getValorUnitario());
    existing.setOcs(replacement.getOcs());
    rep.save(existing);
  }

}
