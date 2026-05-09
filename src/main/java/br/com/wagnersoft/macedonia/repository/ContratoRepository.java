package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Integer> {

	Optional<Contrato> findById(int id);

	@Query("SELECT c FROM Contrato c WHERE c.ocs.id = :id")
	List<Contrato> findByOcs(@Param("id") String id);

}
