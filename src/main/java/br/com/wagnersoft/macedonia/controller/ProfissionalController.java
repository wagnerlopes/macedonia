package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.type.ConselhoEnum;
import br.com.wagnersoft.macedonia.type.UfEnum;
import jakarta.validation.Valid;

/** Profissional Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
public class ProfissionalController {

  private static final Logger logger = LoggerFactory.getLogger(ProfissionalController.class);

  @Autowired
  private CboService cboSvc;

  @Autowired
  private ProfissionalService profSvc;

  public ProfissionalController() {
    super();
    logger.debug("{} loaded", ProfissionalController.class.getSimpleName());
  }

  @ModelAttribute("allProfissionais")
  public List<Profissional> allProfissionais() {
    return profSvc.listAll();
  }

  @ModelAttribute("allCbo")
  public Map<String, String> allCbo() {
    return cboSvc.mapAll();
  }

  @ModelAttribute("allConselho")
  public List<ConselhoEnum> allConselho() {
    return Arrays.asList(ConselhoEnum.ALL);
  }

  @ModelAttribute("allUf")
  public List<UfEnum> allUf() {
    return Arrays.asList(UfEnum.ALL);
  }

  @GetMapping("/especialidades")
  public String especialidades(Model model) {
    logger.info("+++ Especialidades +++");
    model.addAttribute("menu", "esp");
    model.addAttribute("lista", cboSvc.listAll());
    return "especialidades";
  }

  @GetMapping("/profissionais")
  public String show(@RequestParam(name = "cpf", required = false) String cpf, final Profissional profissional, Model model) {
    logger.info("+++ Profissionais +++");
    model.addAttribute("menu", "prof");
    model.addAttribute("profissional", cpf == null ? new Profissional() : profSvc.findByCpf(cpf).orElse(new Profissional()));
    return "profissionais";
  }

  @GetMapping({"/profissionais/delete"})
  public String delete(@RequestParam(name = "cpf", required = false) String cpf) {
    profSvc.remove(cpf);
    return "redirect:/profissionais";
  }

  @PostMapping(value="/profissionais", params={"save"})
  public String save(@Valid final Profissional profissional, final BindingResult bindingResult, final ModelMap model) {
    cboSvc.findById(profissional.getCbo().getCodigo()).ifPresentOrElse(c -> profissional.setCbo(c), () -> bindingResult.rejectValue("cbo.codigo", "profissional.erro.cbo", "Especialidade deve ser informada"));
    if (bindingResult.hasErrors()) {
      return "profissionais";
    }
    final Cbo cbo =cboSvc.findById(profissional.getCbo().getCodigo()).orElseThrow();
    profissional.setCbo(cbo);
    logger.info("{}", profissional);
    profSvc.add(profissional);
    model.clear();
    return "redirect:/profissionais";
  }

}
