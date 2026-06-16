package br.com.wagnersoft.macedonia.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	  if (id == null) return Optional.empty();
		return rep.findById(id);
	}

	public List<Ocs> listAll() {
    final List<Ocs> lista = rep.findAll(Sort.by(Sort.Order.by("descricao").ignoreCase()));
    logger.debug("{}", lista);
    return lista;
	}

  /** Retorna apenas o mapa de nome (value) e o cpf (key) usado em select list.
   * @return {@link Map<String, String>} Id e Descricao
   */
  public Map<Integer, String> mapAll() {
    return this.listAll().stream()
        .collect(Collectors.toMap(Ocs::getId, Ocs::getDescricao, (existing, replacement) -> existing, LinkedHashMap::new));
  }

  public void remove(final Integer id) {
    if (id == null) return;
    rep.findById(id).ifPresent(o -> {
      if (o.getGuias().isEmpty())
        rep.delete(o);
    });
  }
  
	public void add(final Ocs ocs) {
    if (ocs == null) return;
    logger.debug("{}", ocs);
    Optional.ofNullable(ocs.getId())
      .flatMap(rep::findById)
      .ifPresentOrElse(existing -> this.save(existing, ocs), () -> rep.save(ocs));
	}

	public void addProcedimentoMedico(final OcsPm opm) {
    if (opm == null) return;
		final Optional<Ocs> ocs = this.findById(opm.getOcs().getId());
		ocs.ifPresent(o -> {
		  o.addOcsPm(opm);
		  rep.save(o);
		});
	}

	public void removeProcedimentoMedico(final OcsPm opm) {
    if (opm == null) return;
    final Optional<Ocs> ocs = this.findById(opm.getOcs().getId());
    ocs.ifPresent(o -> {
      o.removeOcsPm(opm);
      rep.save(o);
    });
	}

	private void save(final Ocs existing, final Ocs replacement) {
		existing.setCnpj(replacement.getCnpj());
		existing.setComplemento(replacement.getComplemento());
		existing.setContato(replacement.getContato());
		existing.setDescricao(replacement.getDescricao());
		existing.setEndereco(replacement.getEndereco());
		existing.setEspecialidade(replacement.getEspecialidade());
		existing.setMunicipio(replacement.getMunicipio());
		existing.setNumero(replacement.getNumero());
		existing.setRegistroAns(replacement.getRegistroAns());
		existing.setTelefone(replacement.getTelefone());
		existing.setUf(replacement.getUf());
		if (replacement.getProcedimentos() != null) {
		  replacement.getProcedimentos().forEach(op -> {
    	    pmRep.findById(op.getPm().getId()).ifPresent(x -> op.setPm(x));
		    existing.addOcsPm(op);
		  });
		}
		rep.save(existing);
	}
	
}
