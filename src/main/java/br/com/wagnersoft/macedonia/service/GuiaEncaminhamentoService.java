package br.com.wagnersoft.macedonia.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.repository.ProcedimentoMedicoRepository;

/** 
 * Guia de Encaminhamento Service.
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class GuiaEncaminhamentoService {

  private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoService.class);

  @Autowired
  private GuiaEncaminhamentoRepository rep;

  @Autowired
  private ProcedimentoMedicoRepository pmRep;

  public Optional<GuiaEncaminhamento> findById(final Integer id) {
    if (id == null) return Optional.empty();
    return rep.findById(id);
  }

  public List<GuiaEncaminhamento> findByGuiaNr(final String numero) {
    if (numero == null || numero.isBlank()) return Collections.emptyList();
    return rep.findByGuiaNr(numero);
  }

  public List<Object[]> countByMonth(final Integer ano) {
    return rep.countByMonth(ano);
  }

  public List<GuiaEncaminhamento> listAll() {
    final List<GuiaEncaminhamento> lista = rep.findAll(Sort.by(Sort.Order.by("beneficiario_cpf")));
    logger.debug("{}", lista);
    return lista;
  }

  public void remove(final Integer id) {
    if (id == null) return;
    rep.findById(id).ifPresent(c -> rep.delete(c));
  }

  @Transactional
  public void add(final GuiaEncaminhamento guia) {
    if (guia == null) return;
    Optional.ofNullable(guia.getId())
        .flatMap(rep::findById)
        .ifPresentOrElse(existing -> this.save(existing, guia), () -> rep.save(guia));
  }

  private void save(final GuiaEncaminhamento existing, final GuiaEncaminhamento replacement) {
    existing.setBeneficiario(replacement.getBeneficiario());
    existing.setEmissaoData(replacement.getEmissaoData());
    existing.setGuiaNr(replacement.getGuiaNr());
    existing.setObservacao(replacement.getObservacao());
    existing.setOcs(replacement.getOcs());
    existing.setOperador(replacement.getOperador());
    existing.setProtocolo(replacement.getProtocolo());
    existing.setResponsavel(replacement.getResponsavel());
    existing.setSolicitante(replacement.getSolicitante());
    // Totaliza Guia e obtem Procedimento Medico 
    existing.setValorTotal(BigDecimal.ZERO);
    if (replacement.getProcedimentos() != null && !replacement.getProcedimentos().isEmpty()) {
      replacement.getProcedimentos().forEach(p -> {
        existing.setValorTotal(existing.getValorTotal().add(p.getValorTotal()));
        pmRep.findById(p.getPm().getId()).ifPresent(x -> p.setPm(x));
        existing.addGuiaPm(p);
      });
    }
    logger.debug("SAVE = {}", existing);
    rep.save(existing);
  }

}
