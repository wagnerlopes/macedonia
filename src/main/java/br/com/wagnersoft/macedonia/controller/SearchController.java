package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.type.ConselhoEnum;
import br.com.wagnersoft.macedonia.type.EstabelecimentoSaudeEnum;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas as <strong>pesquisas</strong>.
 * <p>
 * Centraliza os endpoints referentes a consulta de <strong>entidades</strong> 
 * no sistema através do caminho base {@code /search}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see BeneficiarioService
 * @see CboService
 * @see GuiaEncaminhamentoService
 * @see GuiaEncaminhamentoViewModelBuilder
 * @see OcsService
 * @see ProcedimentoMedicoService
 * @see ProfissionalService
 */
@Controller
@RequestMapping("/search")
public class SearchController {

  private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

  @Autowired
  private BeneficiarioService benSvc;

  @Autowired
  private CboService cboSvc;

  @Autowired
  private OcsService ocsSvc;

  @Autowired
  private ProcedimentoMedicoService pmSvc;

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

  @PostMapping
  public String search(String tipo, String nome, Model model) {

    var handlers = Map.<String, Function<String, String>>of(
        "beneficiario", n -> { model.addAttribute("allBeneficiarios", benSvc.findByNome(n));
          model.addAttribute("beneficiario", new Beneficiario());
          return "beneficiarios"; },
        "estabelecimento", n -> { model.addAttribute("allOcs", ocsSvc.findByDescricao(n));
          model.addAttribute("allProcedimentos", pmSvc.mapAll());
          model.addAttribute("tipoOcsMap", Arrays.stream(EstabelecimentoSaudeEnum.values()).collect(Collectors.toMap(EstabelecimentoSaudeEnum::getCodigo, EstabelecimentoSaudeEnum::getDescricao)));
          model.addAttribute("ocs", new Ocs());
          return "ocs"; },
        "profissional", n -> { model.addAttribute("allProfissionais", profSvc.findByNome(n));
          model.addAttribute("allCbo", cboSvc.mapAll());
          model.addAttribute("allConselho", Arrays.asList(ConselhoEnum.values()));
          model.addAttribute("profissional", new Profissional());
          return "profissionais"; },
        "guia", n -> { guiaViewModelBuilder.populateGuias(model);
          model.addAttribute("guiaEncaminhamento", new GuiaEncaminhamento());
          model.addAttribute("allGuias", guiaSvc.findByGuiaNr(Integer.valueOf(nome)));
          return "guias"; }
    );

    var view = handlers.get(tipo);
    if (view == null) { throw new IllegalArgumentException("Pesquisa inválida: " + tipo); }
    return view.apply(nome);
  }

}
