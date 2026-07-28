package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Dth;

/**
 * Repositório de dados para a entidade {@link Dth}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de diárias e taxas de estabelecimento de saúde.
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface DthRepository extends JpaRepository<Dth, Integer> {

  @Query("SELECT d FROM Dth d WHERE d.codigo = :codigo")
  List<Dth> findByCodigo(@Param("codigo") String codigo);

}
