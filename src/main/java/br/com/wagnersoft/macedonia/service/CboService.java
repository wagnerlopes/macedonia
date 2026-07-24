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
 * Serviço responsável pelo gerenciamento e pelas regras de negócio da entidade {@link Cbo}.
 * <p>
 * Centraliza as operações de cadastro, atualização, consulta e validações 
 * de domínio das <strong>especialidade de saúde</strong> representadas
 * pela ocupações da área de saúde incluídas na CBO (Classificacao Brasileira de Ocupações).
 * </p>
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

  /**
   * Mapeia todos os CBOs para uma estrutura de chave-valor, onde a chave é o código 
   * e o valor é a descrição. Preserva a ordem original do retorno de {@link #listAll()}.
   *
   * @return {@link Map} contendo os pares (código, descrição) de todos os CBOs.
   */      
  public Map<String, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(Cbo::getCodigo, Cbo::getDescricao, (existing, replacement) -> existing, LinkedHashMap::new));
  }

}
