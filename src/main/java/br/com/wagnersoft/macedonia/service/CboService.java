package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.repository.CboRepository;

@Service
public class CboService {

	private static final Logger logger = LoggerFactory.getLogger(CboService.class);

	@Autowired
	private CboRepository rep;
	
	public List<Cbo> listAllCBO() {
		final List<Cbo> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}
	
	public Optional<Cbo> findById(String id) {
		return rep.findById(id);
	}

}
