package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@Service
public class ProcedimentoMedicoService {

	private static final Logger logger = LoggerFactory.getLogger(ProcedimentoMedicoService.class);

	@Autowired
	private ProcedimentoMedicoRepository rep;

	public List<ProcedimentoMedico> listAll() {
		final List<ProcedimentoMedico> lista = rep.findAll();
		lista.forEach(e -> {logger.debug(e.toString());});
		return lista;
	}

	public Map<Integer, String> mapAll() {
		return listAll().stream().collect(Collectors.toMap(ProcedimentoMedico::getId, ProcedimentoMedico::getDescricao));
	}
	
	public Optional<ProcedimentoMedico> findById(final Integer id) {
		return rep.findById(id);
	}
	
	public void add(ProcedimentoMedico pm) {
		rep.save(pm);
	}
	
}
