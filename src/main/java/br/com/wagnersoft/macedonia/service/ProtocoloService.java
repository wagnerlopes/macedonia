package br.com.wagnersoft.macedonia.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Protocolo;
import br.com.wagnersoft.macedonia.repository.ProtocoloRepository;

@Service
public class ProtocoloService {

	private static final Logger logger = LoggerFactory.getLogger(ProtocoloService.class);

	@Autowired
	private ProtocoloRepository rep;

	public List<Protocolo> listAll() {
		final List<Protocolo> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

	public Ocs findByCnpj(String cnpj) {
		return rep.findByCnpj(cnpj).orElseThrow();
	}

	public void add(Protocolo protocolo) {
		rep.save(protocolo);
	}

}
