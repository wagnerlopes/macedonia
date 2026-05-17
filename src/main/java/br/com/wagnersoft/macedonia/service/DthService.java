package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.repository.DthRepository;

@Service
public class DthService {

	private static final Logger logger = LoggerFactory.getLogger(DthService.class);

	@Autowired
	private DthRepository rep;

	public List<Dth> listAll() {
		final List<Dth> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

	public void add(Dth dth) {
		rep.save(dth);
	}

}
