package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.wagnersoft.macedonia.model.Beneficiario;

/** Beneficiario JPA Repository.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, String> {

	Optional<Beneficiario> findById(String cpf);
	
	@Query("SELECT b FROM Beneficiario b WHERE LOWER(b.nome) LIKE LOWER(CONCAT(:id, '%'))")
	List<Beneficiario> findByNome(@Param("id") String id);

}
