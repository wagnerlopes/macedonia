package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

@Service
public class ProcedimentoService {

	private static final Logger logger = LoggerFactory.getLogger(ProcedimentoService.class);

	@Autowired
	private ProcedimentoMedicoRepository rep;

	public List<ProcedimentoMedico> listar() {
		final List<ProcedimentoMedico> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}
