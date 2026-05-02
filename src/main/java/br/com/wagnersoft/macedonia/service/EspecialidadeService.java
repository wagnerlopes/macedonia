package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Especialidade;
import br.com.wagnersoft.macedonia.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {

	private static final Logger logger = LoggerFactory.getLogger(EspecialidadeService.class);

	@Autowired
	private EspecialidadeRepository rep;

	public List<Especialidade> listar() {
		final List<Especialidade> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

}
