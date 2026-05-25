package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaOcsPm;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.repository.BeneficiarioRepository;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.repository.OcsRepository;
import br.com.wagnersoft.macedonia.repository.ProfissionalRepository;

@Service
public class GuiaEncaminhamentoService {

	private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoService.class);

	@Autowired
	private GuiaEncaminhamentoRepository rep;

	@Autowired
	private BeneficiarioRepository benRep;

	@Autowired
	private OcsRepository ocsRep;

	@Autowired
	private ProfissionalRepository profRep;
	
	public List<GuiaEncaminhamento> listAll() {
		final List<GuiaEncaminhamento> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

	public List<Beneficiario> allBeneficiario() {
		return benRep.findAll();
	}

	public List<Profissional> allProfissional() {
		return profRep.findAll();
	}

	public List<Ocs> allOcs() {
		return ocsRep.findAll();
	}
	
	public Optional<GuiaEncaminhamento> findById(Integer id) {
		return rep.findById(id);
	}
	
	public void add(GuiaEncaminhamento guia) {
		rep.save(guia);
	}

	public void addProcedimentoMedico(GuiaOcsPm gop) {
		final GuiaEncaminhamento guia = rep.getReferenceById(gop.getGuiaEncaminhamento().getId());
		guia.getGuiaOcsPm().add(gop);
		rep.save(guia);
	}

	public void removeProcedimentoMedico(GuiaOcsPm gop) {
		final GuiaEncaminhamento guia = rep.getReferenceById(gop.getGuiaEncaminhamento().getId());
		guia.getGuiaOcsPm().remove(gop);
		rep.save(guia);
	}
	
}
