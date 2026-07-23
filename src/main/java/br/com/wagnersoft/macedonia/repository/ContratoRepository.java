package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wagnersoft.macedonia.model.Contrato;

/**
 * Repositório de dados para a entidade {@link Contrato}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de contrato com estabelecimento de saúde.
 * </p>
 * 
 * @author Wagner Lopes
 * @since 1.0
 * @version 1.0
 */
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

  Optional<Contrato> findById(int id);

  @Query("SELECT c FROM Contrato c WHERE c.ocs.id = :id")
  List<Contrato> findByOcs(@Param("id") String id);

}
