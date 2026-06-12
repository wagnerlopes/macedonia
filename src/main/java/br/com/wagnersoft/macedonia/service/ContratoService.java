package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.repository.ContratoRepository;

/** Contrato com Estabelecimento de Saude service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class ContratoService {

	private static final Logger logger = LoggerFactory.getLogger(ContratoService.class);

	@Autowired
	private ContratoRepository rep;

	public Optional<Contrato> findById(Integer id) {
		return rep.findById(id);
	}

	public List<Contrato> listAll() {
		final List<Contrato> lista = rep.findAll();
		lista.forEach(e -> {logger.info(e.toString());});
		return lista;
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
