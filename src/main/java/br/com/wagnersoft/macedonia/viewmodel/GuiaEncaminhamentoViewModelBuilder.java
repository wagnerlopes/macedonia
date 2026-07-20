package br.com.wagnersoft.macedonia.viewmodel;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;

/** 
 * Inclui no Model os objetos solicitados na View Guias.
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Component
public class GuiaEncaminhamentoViewModelBuilder {

  private final GuiaEncaminhamentoService guiaSvc;

  private final BeneficiarioService benSvc;

  private final ProfissionalService profSvc;

  private final OcsService ocsSvc;

  @SuppressFBWarnings("EI_EXPOSE_REP2")
  public GuiaEncaminhamentoViewModelBuilder(final BeneficiarioService benSvc,
                                            final GuiaEncaminhamentoService guiaSvc,
                                            final OcsService ocsSvc,
                                            final ProfissionalService profSvc) {
    this.benSvc = benSvc; 
    this.guiaSvc = guiaSvc;
    this.ocsSvc = ocsSvc;
    this.profSvc = profSvc;
  }

  public void populateGuias(Model model) {
    model.addAttribute("allBeneficiario", benSvc.mapAll());
    model.addAttribute("allGuias", guiaSvc.listAll());
    model.addAttribute("allOcs", ocsSvc.mapAll());
    model.addAttribute("allProfissional", profSvc.mapAll());
  }

}
