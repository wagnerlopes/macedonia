package br.com.wagnersoft.macedonia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional, String> {

	List<Profissional> findByNome(String nome);

	@Query("SELECT p FROM Profissional p WHERE p.registro.numero = :numero")
	List<Profissional> findByRegistro(@Param("numero") String numero);

	@Query("SELECT p FROM Profissional p WHERE p.cbo.codigo = :codigo ORDER BY p.nome")
	List<Profissional> findByCBO(@Param("codigo") int codigo);
	
}
