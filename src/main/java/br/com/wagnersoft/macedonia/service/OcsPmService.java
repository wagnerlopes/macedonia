package br.com.wagnersoft.macedonia.service;

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

	public List<OcsPm> listAll() {
		final List<OcsPm> lista = rep.findAll();
		return lista;
	}

	public Optional<OcsPm> findById(Integer id) {
		return rep.findById(id);
	}

	public List<OcsPm> findByOcs(Ocs ocs) {
		return rep.findByOcs(ocs);
	}

	public List<OcsPm> findByPm(ProcedimentoMedico pm) {
		return rep.findByPm(pm);
	}
	
	public void add(OcsPm ocspm) {
		logger.debug("{}", ocspm);
		rep.findById(ocspm.getId() == null ? 0 : ocspm.getId()).ifPresentOrElse(old -> save(old, ocspm), () -> rep.save(ocspm));
	}

	public void remove(Integer id) {
		rep.findById(id).ifPresent(o -> {
			if (o.getOcs().getGuias().isEmpty())
				rep.delete(o);
		});
	}
	
	private void save(final OcsPm oldOP, final OcsPm newOP) {
		oldOP.setChQtd(newOP.getChQtd());
		oldOP.setUnidadeMedida(newOP.getUnidadeMedida());
		oldOP.setValorUnitario(newOP.getValorUnitario());
		rep.save(oldOP);
	}
	
}
