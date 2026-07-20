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

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

/** 
 * Procedimento Medico service.
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class ProcedimentoMedicoService {

  private static final Logger logger = LoggerFactory.getLogger(ProcedimentoMedicoService.class);

  @Autowired
  private ProcedimentoMedicoRepository rep;

  public Optional<ProcedimentoMedico> findById(final Integer id) {
    if (id == null) return Optional.empty();
    return rep.findById(id);
  }

  public List<ProcedimentoMedico> listAll() {
    final List<ProcedimentoMedico> lista = rep.findAll(Sort.by(Sort.Order.by("descricao").ignoreCase()));
    logger.debug("{}", lista);
    return lista;
  }

  /** Retorna apenas o mapa de nome (value) e o cpf (key) usado em select list.
   * @return {@link Map<String, String>} Id e Descricao
   */
  public Map<Integer, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(ProcedimentoMedico::getId, ProcedimentoMedico::getDescricao, (existing, replacement) -> existing, LinkedHashMap::new));
  }

}
