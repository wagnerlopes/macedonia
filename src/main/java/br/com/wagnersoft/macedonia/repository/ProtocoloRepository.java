package br.com.wagnersoft.macedonia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Protocolo;

public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {

  @Query("SELECT p FROM Protocolo p WHERE p.ocs.cnpj = :cnpj")
  Optional<Ocs> findByCnpj(String cnpj);

}
