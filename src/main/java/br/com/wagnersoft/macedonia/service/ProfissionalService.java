package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.repository.ProfissionalRepository;

@Service
public class ProfissionalService {

	private static final Logger logger = LoggerFactory.getLogger(ProfissionalService.class);

	@Autowired
	private ProfissionalRepository profRep;

	public List<Profissional> listAll() {
		final List<Profissional> lista = profRep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

	public void add(Profissional prof) {
		profRep.save(prof);
	}

	public void remove(Profissional profissional) {
		profRep.delete(profissional);
	}

}
