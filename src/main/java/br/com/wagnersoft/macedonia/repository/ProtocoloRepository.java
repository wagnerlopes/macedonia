package br.com.wagnersoft.macedonia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Protocolo;

/**
 * Repositório de dados para a entidade {@link Protocolo}.
 * <p>
 * Provê operações de acesso ao banco de dados e consultas customizadas
 * para gerenciamento de protocolo de guia de encaminhamento.
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {

  @Query("SELECT p FROM Protocolo p WHERE p.ocs.cnpj = :cnpj")
  Optional<Ocs> findByCnpj(String cnpj);

}
