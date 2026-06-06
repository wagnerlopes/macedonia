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

	public List<Contrato> listAll() {
		final List<Contrato> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
	}

	public Contrato findById(Integer id) {
		return rep.findById(id).orElse(new Contrato());
	}

	public void remove(final Integer id) {
		rep.findById(id).ifPresent(c -> rep.delete(c));
	}
	
	public void add(Contrato contrato) {
		if (contrato.getId() == null) {
			rep.save(contrato);
		} else {
			rep.findById(contrato.getId()).ifPresentOrElse(oldCont -> save(oldCont, contrato), () -> rep.save(contrato));
		}
	}

	private void save(final Contrato oldCont, final Contrato newCont) {
		oldCont.setChQtd(newCont.getChQtd());
		oldCont.setInicioData(newCont.getInicioData());
		oldCont.setTerminoData(newCont.getTerminoData());
		oldCont.setOcs(newCont.getOcs());
		rep.save(oldCont);
	}
	
}
