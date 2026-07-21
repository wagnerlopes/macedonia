package br.com.wagnersoft.macedonia.controller;

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

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.service.ContratoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas aos <strong>contratos de saúde</strong>.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>contratos</strong> no sistema através do caminho base {@code /contratos}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see ContratoService
 * @see OcsService
 */
@Controller
@RequestMapping("/contratos")
public class ContratoController {

  private static final Logger logger = LoggerFactory.getLogger(ContratoController.class);

  @Autowired
  private OcsService ocsSvc;

  @Autowired
  private ContratoService cttSvc;

  public ContratoController() {
    super();
    logger.debug("{} loaded", ContratoController.class.getSimpleName());
  }

  @ModelAttribute("allContratos")
  public List<Contrato> listContratos() {
    return cttSvc.listAll();
  }

  @ModelAttribute("allOcs")
  public Map<Integer, String> listEstabelecimento() {
    return ocsSvc.mapAll();
  }

  @GetMapping
  public String show(Integer id, Model model) {
    logger.info("+++ Contratos +++");
    model.addAttribute("menu", "Contrato");
    model.addAttribute("contrato", id == null ? new Contrato() : cttSvc.findById(id).orElse(new Contrato()));
    return "contratos";
  }

  @GetMapping({"/delete"})
  public String delete(Integer id) {
    cttSvc.remove(id);
    return "redirect:/contratos";
  }

  @PostMapping(value = "/save", params = "save")
  public String save(@Valid Contrato contrato, BindingResult bindingResult, Model model) {
    if (contrato.getOcs().getId() == null) {
      bindingResult.rejectValue("ocs.id", "contrato.erro.ocs", "Deve ser informado");
    }
    else {
      ocsSvc.findById(contrato.getOcs().getId()).ifPresentOrElse(o -> contrato.setOcs(o), () -> bindingResult.rejectValue("ocs.id", "contrato.erro.ocs", "Deve ser informado"));
    }
    if (bindingResult.hasErrors()) {
      return "contratos";
    }
    
    logger.info("{}", contrato);
    cttSvc.add(contrato);
    
    return "redirect:/contratos";
  }

}
