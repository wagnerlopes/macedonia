package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.type.ConselhoEnum;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>profissionais de saúde</strong>.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>profissionais de saúde</strong> no sistema através do caminho base {@code /profissionais}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see CboService
 * @see ProfissionalService
 */
@Controller
@RequestMapping("/profissionais")
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
    return Arrays.asList(ConselhoEnum.values());
  }

  @GetMapping
  public String show(String cpf, Model model) {
    logger.info("+++ Profissionais +++");
    model.addAttribute("menu", "prof");
    model.addAttribute("profissional", cpf == null ? new Profissional() : profSvc.findByCpf(cpf).orElse(new Profissional()));
    return "profissionais";
  }

  @PostMapping(params = "delete")
  public String delete(String cpf) {
    profSvc.remove(cpf);
    return "redirect:/profissionais";
  }

  @PostMapping(params = "save")
  public String save(@Valid Profissional profissional, BindingResult bindingResult, Model model) {
    cboSvc.findById(profissional.getCbo().getCodigo()).ifPresentOrElse(c -> profissional.setCbo(c), () -> bindingResult.rejectValue("cbo.codigo", "profissional.erro.cbo", "Especialidade deve ser informada"));

    if (bindingResult.hasErrors()) {
      return "profissionais";
    }
    
    final Cbo cbo = cboSvc.findById(profissional.getCbo().getCodigo()).orElseThrow();
    profissional.setCbo(cbo);
    
    logger.debug("{}", profissional);
    profSvc.add(profissional);
    
    return "redirect:/profissionais";
  }

}
