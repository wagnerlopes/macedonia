package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;

@Service
public class GuiaEncaminhamentoService {

	private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoService.class);

	@Autowired
	private GuiaEncaminhamentoRepository rep;

	public List<GuiaEncaminhamento> listar() {
		final List<GuiaEncaminhamento> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}
