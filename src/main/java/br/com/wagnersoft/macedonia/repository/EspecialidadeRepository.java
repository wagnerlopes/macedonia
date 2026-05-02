package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	List<Especialidade> findByDescricao(String descricao);

	Optional<Especialidade> findById(long id);

	@Query("SELECT e FROM Especialidade e WHERE UPPER(e.descricao) LIKE :descricao")
	List<Especialidade> findByOutros(@Param("descricao") String descricao);

}
