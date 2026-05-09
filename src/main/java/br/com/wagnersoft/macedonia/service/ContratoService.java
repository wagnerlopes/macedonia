package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.repository.ContratoRepository;

@Service
public class ContratoService {

	private static final Logger logger = LoggerFactory.getLogger(ContratoService.class);

	@Autowired
	private ContratoRepository rep;

	public List<Contrato> listar() {
		final List<Contrato> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}
