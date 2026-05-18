package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, String> {

	List<Profissional> findByNome(String nome);

	@Query("SELECT p FROM Profissional p WHERE p.crm = :crm")
	List<Profissional> findByCrm(@Param("crm") String crm);

	@Query("SELECT p FROM Profissional p WHERE p.especialidade.codigo = :codigo ORDER BY p.nome")
	List<Profissional> findByEspecialidade(@Param("codigo") int codigo);
	
}
