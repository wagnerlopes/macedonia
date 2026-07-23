package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Ocs;

/**
 * Repositório de dados para a entidade {@link Ocs}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de estabelecimento de saúde.
 * </p>
 * 
 * @author Wagner Lopes
 * @since 1.0
 * @version 1.0
 */
public interface OcsRepository extends JpaRepository<Ocs, Integer> {

  Optional<Ocs> findByCnpj(String cnpj);

  @Query("SELECT o FROM Ocs o WHERE LOWER(o.descricao) LIKE LOWER(CONCAT(:descricao, '%'))")
  List<Ocs> findByDescricao(@Param("descricao") String descricao);

  List<Ocs> findByEspecialidade(String especialidade);

}
