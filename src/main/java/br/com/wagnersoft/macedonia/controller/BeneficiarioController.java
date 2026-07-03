package br.com.wagnersoft.macedonia.controller;

import java.util.List;

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

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/** Beneficiario Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
@RequestMapping("/beneficiarios")
public class BeneficiarioController {

  private static final Logger logger = LoggerFactory.getLogger(BeneficiarioController.class);

  @Autowired
  private BeneficiarioService benSvc;

  public BeneficiarioController() {
    super();
    logger.debug("{} loaded", BeneficiarioController.class.getSimpleName());
  }

  @ModelAttribute("allBeneficiarios")
  public List<Beneficiario> listBeneficiarios() {
    return benSvc.listAll();
  }

  @GetMapping
  public String show(@RequestParam(name = "cpf", required = false) String cpf, final Beneficiario beneficiario, Model model) {
    logger.info("+++ Beneficiarios +++");
    model.addAttribute("menu", "ben");
    model.addAttribute("beneficiario", cpf  == null ? new Beneficiario() : benSvc.findByCpf(cpf));
    return "beneficiarios";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam(name = "cpf", required = false) String cpf) {
    benSvc.remove(cpf);
    return "redirect:/beneficiarios";
  }

  @PostMapping("/save")
  public String save(@Valid final Beneficiario beneficiario, final BindingResult bindingResult, final HttpServletRequest req, final ModelMap model) {
    if (bindingResult.hasErrors()) {
      return "beneficiarios";
    }
    logger.debug("{}", beneficiario);
    benSvc.add(beneficiario);
    model.clear();
    return "redirect:/beneficiarios";
  }

}
