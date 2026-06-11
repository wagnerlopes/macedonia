package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.repository.OcsRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

/** OCS (Estabelecimento de Saude) service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class OcsService {

	private static final Logger logger = LoggerFactory.getLogger(OcsService.class);

	@Autowired
	private OcsRepository rep;

	@Autowired
	private ProcedimentoMedicoRepository pmRep;

	public Optional<Ocs> findById(Integer id) {
		return rep.findById(id);
	}

	public Optional<Ocs> findByCnpj(String cnpj) {
		return rep.findByCnpj(cnpj);
	}

	public Map<Integer, String> mapAll() {
		return listAll().stream().collect(Collectors.toMap(Ocs::getId, Ocs::getDescricao));
	}

	public List<Ocs> listAll() {
		final List<Ocs> lista = rep.findAll();
		return lista;
	}

	public void add(Ocs ocs) {
		logger.debug("{}", ocs);
		rep.findById(ocs.getId()).ifPresentOrElse(oldOcs -> save(oldOcs, ocs), () -> rep.save(ocs));
	}

	public void remove(Integer id) {
		rep.findById(id).ifPresent(o -> {
			if (o.getGuias().isEmpty())
				rep.delete(o);
		});
	}
	
	public void addProcedimentoMedico(OcsPm ocsPm) {
		final Ocs ocs = rep.getReferenceById(ocsPm.getOcs().getId());
		ocs.getProcedimentos().add(ocsPm);
		rep.save(ocs);
	}

	public void removeProcedimentoMedico(OcsPm ocsPm) {
		final Ocs ocs = rep.getReferenceById(ocsPm.getOcs().getId());
		ocs.getProcedimentos().remove(ocsPm);
		rep.save(ocs);
	}

	private void save(final Ocs oldOcs, final Ocs newOcs) {
		oldOcs.setCnpj(newOcs.getCnpj());
		oldOcs.setComplemento(newOcs.getComplemento());
		oldOcs.setContato(newOcs.getContato());
		oldOcs.setDescricao(newOcs.getDescricao());
		oldOcs.setEndereco(newOcs.getEndereco());
		oldOcs.setEspecialidade(newOcs.getEspecialidade());
		oldOcs.setMunicipio(newOcs.getMunicipio());
		oldOcs.setNumero(newOcs.getNumero());
		oldOcs.setRegistroAns(newOcs.getRegistroAns());
		oldOcs.setTelefone(newOcs.getTelefone());
		oldOcs.setUf(newOcs.getUf());
		if (newOcs.getProcedimentos() != null) {
		  newOcs.getProcedimentos().forEach(op -> {
    	    pmRep.findById(op.getPm().getId()).ifPresent(x -> op.setPm(x));
		    oldOcs.addOcsPm(op);
		  });
		}
		rep.save(oldOcs);
	}
	
}
