package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;

public interface ProcedimentoMedicoRepository extends JpaRepository<ProcedimentoMedico, Integer> {

	List<ProcedimentoMedico> findByDescricao(String descricao);

	Optional<ProcedimentoMedico> findById(int id);

	@Query("SELECT o FROM Ocs o WHERE o.cnpj = :cnpj")
	List<ProcedimentoMedico> findByAmb90(@Param("amb90") String amb90);

	@Query("SELECT p FROM ProcedimentoMedico p WHERE p.tuss = :tuss")
	List<ProcedimentoMedico> findByTuss(@Param("tuss") String tuss);

}
