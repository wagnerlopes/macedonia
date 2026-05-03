package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Ocs;

public interface OcsRepository extends JpaRepository<Ocs, Integer> {

	List<Ocs> findByDescricao(String descricao);

	Optional<Ocs> findById(int id);

	@Query("SELECT o FROM Ocs o WHERE o.cnpj = :cnpj")
	List<Ocs> findByCnpj(@Param("cnpj") String cnpj);

	@Query("SELECT o FROM Ocs o WHERE o.especialidade = :esp ORDER BY o.descricao")
	List<Ocs> findByEsp(@Param("esp") String esp);
	
}
