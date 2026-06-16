package br.com.wagnersoft.macedonia.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.Protocolo;
import br.com.wagnersoft.macedonia.repository.ProtocoloRepository;

/** Protocolo de Guia de Encaminhamento Service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class ProtocoloService {

	private static final Logger logger = LoggerFactory.getLogger(ProtocoloService.class);

	@Autowired
	private ProtocoloRepository rep;

  public void delete(final Integer id) {
    if (id == null) return;
    this.findById(id).ifPresent(p -> rep.delete(p));
  }
  
	public Optional<Protocolo> findById(final Integer id) {
	  if (id == null) return Optional.empty();
		return rep.findById(id);
	}
	
	public List<Protocolo> listAll() {
		final List<Protocolo> lista = rep.findAll();
    logger.debug("{}", lista);
		return lista;
	}

	public void add(final Protocolo protocolo) {
	  if (protocolo == null) return;
    logger.debug("{}", protocolo);
	  Optional.of(protocolo.getId())
	    .flatMap(rep::findById)
	    .ifPresentOrElse(existing -> this.save(existing, protocolo), () -> rep.save(protocolo));
	}

  private void save(final Protocolo existing, final Protocolo replacement) {
    existing.setAssunto(replacement.getAssunto());
    existing.setDestino(replacement.getDestino());
    existing.setDocData(replacement.getDocData());
    existing.setDocNr(replacement.getDocNr());
    existing.setDocTipo(replacement.getDocTipo());
    existing.setGuias(replacement.getGuias());
    existing.setObservacao(replacement.getObservacao());
    existing.setOcs(replacement.getOcs());
    existing.setStatus(replacement.getStatus());
    existing.setValor(replacement.getValor());
  }

}
