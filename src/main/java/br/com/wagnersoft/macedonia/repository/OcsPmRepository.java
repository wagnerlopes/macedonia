package br.com.wagnersoft.macedonia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;

/**
 * OcsPm repository. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public interface OcsPmRepository extends JpaRepository<OcsPm, Integer> {

  Optional<OcsPm> findById(int id);

  List<OcsPm> findByOcs(Ocs ocs);

  List<OcsPm> findByPm(ProcedimentoMedico pm);

  @Query("SELECT o FROM OcsPm o WHERE o.ocs.id = :ocs_id AND o.pm.id = :pm_id")
  Optional<OcsPm> findByOcsPm(@Param("ocs_id") Integer ocsId, @Param("pm_id") Integer pmId);

}
