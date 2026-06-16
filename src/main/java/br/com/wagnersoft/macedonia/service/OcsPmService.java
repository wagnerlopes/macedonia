package br.com.wagnersoft.macedonia.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.OcsPmRepository;

/** OcsPm Service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class OcsPmService {

	private static final Logger logger = LoggerFactory.getLogger(OcsPmService.class);

	@Autowired
	private OcsPmRepository rep;

	public Optional<OcsPm> findById(final Integer id) {
	  if (id == null) return Optional.empty();
		return rep.findById(id);
	}

	public List<OcsPm> findByOcs(final Ocs ocs) {
	  if (ocs == null) return Collections.emptyList();
		return rep.findByOcs(ocs);
	}

	public List<OcsPm> findByPm(final ProcedimentoMedico pm) {
    if (pm == null) return Collections.emptyList();
		return rep.findByPm(pm);
	}
	
  public List<OcsPm> listAll() {
    final List<OcsPm> lista = rep.findAll();
    return lista;
  }
  
  public void remove(final Integer id) {
    if (id == null) return;
    rep.findById(id).ifPresent(o -> {
      if (o.getOcs().getGuias().isEmpty())
        rep.delete(o);
    });
  }
  
	public void add(final OcsPm opm) {
	  if (opm == null) return;
	  Optional.ofNullable(opm.getId())
	    .flatMap(rep::findById)
	    .ifPresentOrElse(existing -> save(existing, opm), () -> rep.save(opm));
	}

	private void save(final OcsPm existing, final OcsPm replacement) {
    logger.debug("existing: {}", existing);
    logger.debug("replacement: {}", replacement);
		existing.setChQtd(replacement.getChQtd());
		existing.setUnidadeMedida(replacement.getUnidadeMedida());
		existing.setValorUnitario(replacement.getValorUnitario());
		rep.save(existing);
	}
	
}
