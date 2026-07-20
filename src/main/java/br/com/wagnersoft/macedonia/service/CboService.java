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

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.repository.CboRepository;

/** 
 * CBO (Classificacao Brasileira de Ocupacoes) service.
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class CboService {

  private static final Logger logger = LoggerFactory.getLogger(CboService.class);

  @Autowired
  private CboRepository rep;

  public Optional<Cbo> findById(String id) {
    if (id == null || id.isBlank()) return Optional.empty();
    return rep.findById(id);
  }

  public List<Cbo> listAll() {
    final List<Cbo> lista = rep.findAll(Sort.by(Sort.Order.by("descricao").ignoreCase()));
    logger.debug("{}", lista);
    return lista;
  }

  /** Retorna apenas o mapa de descricao (value) e o codigo (key) usado em select list.
   * @return {@link Map<String, String>} codigo e descricao
   */
  public Map<String, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(Cbo::getCodigo, Cbo::getDescricao, (existing, replacement) -> existing, LinkedHashMap::new));
  }

}
