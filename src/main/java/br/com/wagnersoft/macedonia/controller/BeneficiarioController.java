package br.com.wagnersoft.macedonia.controller;

import java.util.List;

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

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>beneficiários</strong>.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>beneficiários</strong> no sistema através do caminho base {@code /beneficiarios}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see BeneficiarioService
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
  public String show(String cpf, Model model) {
    logger.info("+++ Beneficiarios +++");
    model.addAttribute("menu", "ben");
    model.addAttribute("beneficiario", cpf  == null ? new Beneficiario() : benSvc.findByCpf(cpf));
    return "beneficiarios";
  }

  @PostMapping(params = "delete")
  public String delete(String cpf) {
    benSvc.remove(cpf);
    return "redirect:/beneficiarios";
  }

  @PostMapping(params = "save")
  public String save(@Valid Beneficiario beneficiario, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "beneficiarios";
    }
    
    logger.debug("{}", beneficiario);
    benSvc.add(beneficiario);
    
    return "redirect:/beneficiarios";
  }

}
