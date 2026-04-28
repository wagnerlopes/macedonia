package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	List<Especialidade> findByDescricao(String descricao);

	Optional<Especialidade> findById(long id);
	
}
