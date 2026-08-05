package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.type.EstabelecimentoSaudeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>estabelecimentos de saúde</strong>.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>estabelecimentos de saúde</strong> no sistema através do caminho base {@code /ocs}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see OcsService
 * @see ProcedimentoMedicoService
 */
@Controller
@RequestMapping("/ocs")
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

  @ModelAttribute("tipoOcsMap")
  public Map<String, String> tipoOcsMap() {
    return Arrays.stream(EstabelecimentoSaudeEnum.values()).collect(Collectors.toMap(EstabelecimentoSaudeEnum::getCodigo, EstabelecimentoSaudeEnum::getDescricao));
  }

  @GetMapping
  public String show(Integer id, Model model) {
    logger.info("+++ OCS +++");
    model.addAttribute("menu", "ocs");

    Ocs ocsObj = new Ocs();
    if (id != null) {
      Optional<Ocs> ocsOpt = ocsSvc.findById(id);
      if (ocsOpt.isPresent()) {
        ocsObj = ocsOpt.get();
      }
    }

    model.addAttribute("ocs", ocsObj);
    model.addAttribute("procedimentos", ocsObj.getProcedimentos());

    return "ocs";
  }

  @PostMapping(params = "delete")
  public String delete(Integer id) {
    ocsSvc.remove(id);
    return "redirect:/ocs";
  }

  @PostMapping(params = "save")
  public String save(@Valid Ocs ocs, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "ocs";
    }

    logger.debug("{}", ocs);
    ocsSvc.add(ocs);

    return "redirect:/ocs";
  }

  @PostMapping(params = "addRow")
  public String addRow(Ocs ocs, BindingResult bindingResult, Model model) {
    ocs.getProcedimentos().add(new OcsPm());
    model.addAttribute("ocs", ocs);
    model.addAttribute("procedimentos", ocs.getProcedimentos());
    return "ocs";
  }

  @PostMapping(params = "removeRow")
  public String removeRow(Ocs ocs, BindingResult bindingResult, HttpServletRequest req, Model model) {
    final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
    ocs.getProcedimentos().remove(rowId.intValue());
    model.addAttribute("ocs", ocs);
    model.addAttribute("procedimentos", ocs.getProcedimentos());
    return "ocs";
  }

}
