package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Cbo;

/**
 * Repositório de dados para a entidade {@link Cbo}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de cbo, neste sistema apenas um subconjunto das
 * ocupações da área de saúde.
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface CboRepository extends JpaRepository<Cbo, String> {

  /**
   * Busca cbo cuja descricao inicie com o termo informado (case-insensitive).
   *
   * @param descricao O prefixo ou descricao para filtragem (ex: "med" busca "Medico", "Enfermeiro", etc.).
   * @return Lista de cbo correspondentes ao filtro.
   */
  @Query("SELECT c FROM Cbo c WHERE LOWER(c.descricao) LIKE LOWER(CONCAT(:descricao, '%'))")
  List<Cbo> findByDescricao(@Param("descricao") String descricao);

}
