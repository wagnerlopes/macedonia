package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.type.EstabelecimentoSaudeEnum;
import br.com.wagnersoft.macedonia.type.UfEnum;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/** OCS (Estabelecimento de Saude) Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
public class OcsController {

  private static final Logger logger = LoggerFactory.getLogger(OcsController.class);

  @Autowired
  private OcsService ocsSvc;

  @Autowired
  private ProcedimentoMedicoService pmSvc;

  public OcsController() {
    super();
    logger.debug("{} loaded", OcsController.class.getSimpleName());
  }

  @ModelAttribute("allOcs")
  public List<Ocs> allOcs() {
    return ocsSvc.listAll();
  }

  @ModelAttribute("allProcedimentos")
  public Map<Integer, String> allProcedimentos() {
    return pmSvc.mapAll();
  }

  @ModelAttribute("allEspecialidade")
  public List<EstabelecimentoSaudeEnum> allEspecialidade() {
    return Arrays.asList(EstabelecimentoSaudeEnum.ALL);
  }

  @ModelAttribute("allUf")
  public List<UfEnum> allUf() {
    return Arrays.asList(UfEnum.ALL);
  }

  @ModelAttribute("tipoOcsMap")
  public Map<String, String> tipoOcsMap() {
    return Arrays.stream(EstabelecimentoSaudeEnum.values()).collect(Collectors.toMap(EstabelecimentoSaudeEnum::getCodigo, EstabelecimentoSaudeEnum::getDescricao));
  }

  @ModelAttribute("unidadeMedidaMap")
  public Map<String, String> unidadeMedidaMap() {
    return Arrays.stream(UnidadeMedidaEnum.values()).collect(Collectors.toMap(UnidadeMedidaEnum::getCodigo, UnidadeMedidaEnum::getDescricao));
  }

  @GetMapping("/ocs")
  public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
    logger.info("+++ OCS +++");
    model.addAttribute("menu", "ocs");
    model.addAttribute("ocs", id == null ? new Ocs() : ocsSvc.findById(id).orElse(new Ocs()));
    model.addAttribute("procedimentos", id != null && ocsSvc.findById(id).isPresent() ? ocsSvc.findById(id).get().getProcedimentos() : new OcsPm());
    return "ocs";
  }

  @GetMapping({"/ocs/delete"})
  public String delete(@RequestParam(name = "id", required = false) Integer id) {
    ocsSvc.remove(id);
    return "redirect:/ocs";
  }

  @RequestMapping(value="/ocs", params={"save"})
  public String save(@Valid final Ocs ocs, final BindingResult bindingResult, final ModelMap model) {
    if (bindingResult.hasErrors()) {
      return "ocs";
    }
    logger.info("{}", ocs);
    ocsSvc.add(ocs);
    model.clear();
    return "redirect:/ocs";
  }

  @RequestMapping(value="/ocs", params={"addRow"})
  public String addRow(final Ocs ocs, final BindingResult bindingResult, final Model model) {
    ocs.getProcedimentos().add(new OcsPm());
    model.addAttribute("ocs", ocs);
    model.addAttribute("procedimentos", ocs.getProcedimentos());
    return "ocs";
  }

  @RequestMapping(value="/ocs", params={"removeRow"})
  public String removeRow(final Ocs ocs, final BindingResult bindingResult, final HttpServletRequest req, final Model model) {
    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
    ocs.getProcedimentos().remove(rowId.intValue());
    model.addAttribute("ocs", ocs);
    model.addAttribute("procedimentos", ocs.getProcedimentos());
    return "ocs";
  }

}
