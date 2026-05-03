package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.repository.OcsRepository;

@Service
public class OcsService {

	private static final Logger logger = LoggerFactory.getLogger(OcsService.class);

	@Autowired
	private OcsRepository rep;

	public List<Ocs> listar() {
		final List<Ocs> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}
