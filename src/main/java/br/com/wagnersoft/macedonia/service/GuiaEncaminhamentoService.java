package br.com.wagnersoft.macedonia.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

/** Guia de Encaminhamento Service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class GuiaEncaminhamentoService {

	private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoService.class);

	@Autowired
	private GuiaEncaminhamentoRepository rep;

	@Autowired
	private ProcedimentoMedicoRepository pmRep;

	public Optional<GuiaEncaminhamento> findById(final Integer id) {
		return rep.findById(id);
	}
	
	public List<GuiaEncaminhamento> listAll() {
		final List<GuiaEncaminhamento> lista = rep.findAll();
		lista.forEach(e -> {logger.debug("LIST = {}", e.toString());});
		return lista;
	}

	public void add(GuiaEncaminhamento guia) {
	  if (guia == null) return;
	  Optional.ofNullable(guia.getId())
  	  .flatMap(rep::findById)
	    .ifPresentOrElse(existing -> this.save(existing, guia), () -> rep.save(guia));	  
	}

	private void save(final GuiaEncaminhamento existing, final GuiaEncaminhamento guia) {
		existing.setBeneficiario(guia.getBeneficiario());
		existing.setEmissaoData(guia.getEmissaoData());
		existing.setGuiaNr(guia.getGuiaNr());
		existing.setObservacao(guia.getObservacao());
		existing.setOcs(guia.getOcs());
		existing.setOperador(guia.getOperador());
		existing.setProtocolo(guia.getProtocolo());
		existing.setResponsavel(guia.getResponsavel());
		existing.setSolicitante(guia.getSolicitante());
		// Totaliza Guia e obtem Procedimento Medico 
		existing.setValorTotal(BigDecimal.ZERO);
		if (guia.getProcedimentos() != null && !guia.getProcedimentos().isEmpty()) {
			  guia.getProcedimentos().forEach(p -> {
				existing.setValorTotal(existing.getValorTotal().add(p.getValorTotal()));
 	    	    pmRep.findById(p.getPm().getId()).ifPresent(x -> p.setPm(x));
			    existing.addGuiaPm(p);
			  });
		}
		logger.debug("SAVE = {}", existing);
		rep.save(existing);
	}
	
	public void addProcedimento(GuiaPm gop) {
		final GuiaEncaminhamento guia = rep.getReferenceById(gop.getGuiaEncaminhamento().getId());
		guia.getProcedimentos().add(gop);
		rep.save(guia);
	}

	public void removeProcedimento(GuiaPm gop) {
		final GuiaEncaminhamento guia = rep.getReferenceById(gop.getGuiaEncaminhamento().getId());
		guia.getProcedimentos().remove(gop);
		rep.save(guia);
	}
	
}
