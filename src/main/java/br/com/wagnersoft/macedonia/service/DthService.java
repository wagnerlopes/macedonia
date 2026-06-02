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

	public Dth findById(Integer id) {
		return rep.findById(id).orElse(new Dth());
	}

	public void remove(final Integer id) {
		rep.findById(id).ifPresent(c -> rep.delete(c));
	}
	
	public void add(Dth dth) {
		if (dth.getId() == null) {
			rep.save(dth);
		} else {
			rep.findById(dth.getId()).ifPresentOrElse(oldDth -> save(oldDth, dth), () -> rep.save(dth));
		}
	}

	private void save(final Dth oldDth, final Dth newDth) {
		oldDth.setDescricao(newDth.getDescricao());
		oldDth.setCodigo(newDth.getCodigo());
		oldDth.setUnidadeMedida(newDth.getUnidadeMedida());
		oldDth.setValorUnitario(newDth.getValorUnitario());
		oldDth.setOcs(newDth.getOcs());
		rep.save(oldDth);
	}
	
}
