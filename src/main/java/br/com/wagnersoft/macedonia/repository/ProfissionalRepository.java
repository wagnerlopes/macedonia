package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, String> {

	List<Profissional> findByNome(String nome);

	Optional<Profissional> findById(String cpf);

	@Query("SELECT p FROM Profissional p WHERE p.crm = :crm")
	List<Profissional> findByCnpj(@Param("crm") String crm);

	@Query("SELECT p FROM Profissional p WHERE p.especialidade.codigo = :esp ORDER BY p.nome")
	List<Profissional> findByEsp(@Param("esp") int esp);
	
}
