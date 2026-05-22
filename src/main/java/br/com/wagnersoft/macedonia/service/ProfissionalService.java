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

	public Profissional findByCpf(String cpf) {
		return profRep.findById(cpf).orElseThrow();
	}

	public void add(Profissional profissional) {
		profRep.findById(profissional.getCpf()).ifPresentOrElse(oldProf -> save(oldProf, profissional), () -> profRep.save(profissional));;
		profRep.save(profissional);
	}

	public void remove(String cpf) {
		profRep.findById(cpf).ifPresent(p -> {
			if (p.getGuiasResponsavel().isEmpty() && p.getGuiasSolicitante().isEmpty())
				profRep.delete(p);
		});
	}

	private void save(final Profissional oldProf, final Profissional newProf) {
		oldProf.setNome(newProf.getNome());
		oldProf.setCbo(newProf.getCbo());
		oldProf.setCns(newProf.getCns());
		oldProf.setRegistroProfissional(newProf.getRegistroProfissional());
		profRep.save(oldProf);
	}
	
}
