package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.Ocs;

public interface OcsRepository extends JpaRepository<Ocs, Integer> {

	Optional<Ocs> findByCnpj(String cnpj);

	List<Ocs> findByDescricao(String descricao);

	List<Ocs> findByEspecialidade(String especialidade);
	
}
