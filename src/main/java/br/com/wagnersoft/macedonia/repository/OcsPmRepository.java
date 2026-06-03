package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;

public interface OcsPmRepository extends JpaRepository<OcsPm, Integer> {

	Optional<OcsPm> findById(int id);

	List<OcsPm> findByOcs(Ocs ocs);

	List<OcsPm> findByPm(ProcedimentoMedico pm);

}
