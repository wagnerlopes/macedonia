package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.service.OcsPmService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>procedimentos no estabelecimentos de saúde</strong>.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>procedimentos nos estabelecimentos</strong> no sistema através do caminho base {@code /ocspm}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
@RequestMapping("/ocspm")
public class OcsPmController {

  private static final Logger logger = LoggerFactory.getLogger(OcsPmController.class);

  @Autowired
  private OcsService ocsSvc;

  @Autowired
  private OcsPmService ocsPmSvc;

  @Autowired
  private ProcedimentoMedicoService pmSvc;

  public OcsPmController() {
    super();
    logger.debug("{} loaded", OcsPmController.class.getSimpleName());
  }

  @ModelAttribute("allProcedimentos")
  public Map<Integer, String> allProcedimentos() {
    return pmSvc.mapAll();
  }

  @ModelAttribute("unidadeMedidaMap")
  public Map<String, String> unidadeMedidaMap() {
    return Arrays.stream(UnidadeMedidaEnum.values()).collect(Collectors.toMap(UnidadeMedidaEnum::getCodigo, UnidadeMedidaEnum::getDescricao));
  }

  @GetMapping
  public String show(@RequestParam(name = "ocsid", required = true) Integer ocsId, @RequestParam(name = "id", required = false, defaultValue = "0") Integer id, Model model) {
    logger.info("+++ OCS/PM +++");
    model.addAttribute("menu", "ocspm");
    final Ocs ocs = ocsSvc.findById(ocsId).orElse(new Ocs(ocsId));
    model.addAttribute("ocs", ocs);
    model.addAttribute("listOcsPm", ocsPmSvc.findByOcs(ocs));
    model.addAttribute("ocspm", ocsPmSvc.findById(id).orElse(new OcsPm()));
    return "ocspm";
  }

  @GetMapping({"/delete"})
  public String delete(@RequestParam(name = "ocsid", required = true) Integer ocsId, @RequestParam(name = "id", required = true) Integer id) {
    ocsPmSvc.remove(id);
    return "redirect:/ocspm?ocsid=" + ocsId;
  }

  @PostMapping(value = "/save", params = {"save"})
  public String save(@Valid final OcsPm ocspm, final BindingResult bindingResult, final ModelMap model) {
    if (bindingResult.hasErrors()) {
      return "ocspm";
    }
    logger.info("{}", ocspm);
    ocsPmSvc.add(ocspm);
    model.clear();
    return "redirect:/ocspm?ocsid=" + ocspm.getOcs().getId();
  }

}
