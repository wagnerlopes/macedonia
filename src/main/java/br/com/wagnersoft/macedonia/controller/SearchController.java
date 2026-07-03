package br.com.wagnersoft.macedonia.controller;

import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

/** Search Controller.
 * Os Model Attributes necessários na view Guias são carregados em
 * {@link GuiaEncaminhamentoViewModelBuilder} através do {@link GuiaEncaminhamentoViewModelAdvice}.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
public class SearchController {

  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

  @Autowired
  private BeneficiarioService benSvc;

  @Autowired
  private OcsService ocsSvc;

  @Autowired
  private ProfissionalService profSvc;

  @Autowired
  private GuiaEncaminhamentoService guiaSvc;

  @Autowired
  private GuiaEncaminhamentoViewModelBuilder guiaViewModelBuilder;
  
  public SearchController() {
    super();
    logger.debug("{} loaded", SearchController.class.getSimpleName());
  }

  @PostMapping("/search")
  public String search(@RequestParam("tipo") String tipo, @RequestParam("nome") String nome, Model model) {

    var handlers = Map.<String, Function<String, String>>of(
        "beneficiario", n -> { model.addAttribute("allBeneficiarios", benSvc.findByNome(n));
                               model.addAttribute("beneficiario", new Beneficiario());
                               return "beneficiarios"; },
        "estabelecimento", n -> { model.addAttribute("allOcs", ocsSvc.findByDescricao(n));
                                  model.addAttribute("ocs", new Ocs());
                                  return "ocs"; },
        "profissional", n -> { model.addAttribute("allProfissionais", profSvc.findByNome(n));
                               model.addAttribute("profissional", new Profissional());
                               return "profissionais"; },
        "guia", n -> { guiaViewModelBuilder.populateGuias(model);
                       model.addAttribute("guiaEncaminhamento", new GuiaEncaminhamento());
                       model.addAttribute("allGuias", guiaSvc.findByGuiaNr(n));
                       return "guias"; }
        );

    var view = handlers.get(tipo);
    if (view == null) throw new IllegalArgumentException("Pesquisa inválida: " + tipo);
    return view.apply(nome);
  }

}
