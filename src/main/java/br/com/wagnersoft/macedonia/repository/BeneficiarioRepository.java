package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wagnersoft.macedonia.model.Beneficiario;

/**
 * Repositório de dados para a entidade {@link Beneficiario}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de beneficiários.
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, String> {

  /**
   * Busca beneficiários cujo nome inicie com o termo informado (case-insensitive).
   *
   * @param nome O prefixo ou nome para filtragem (ex: "mar" busca "Maria", "Mário", etc.).
   * @return Lista de beneficiários correspondentes ao filtro.
   */
  @Query("SELECT b FROM Beneficiario b WHERE LOWER(b.nome) LIKE LOWER(CONCAT(:nome, '%'))")
  List<Beneficiario> findByNome(@Param("nome") String nome);

}
