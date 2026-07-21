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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.service.DthService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;
import jakarta.validation.Valid;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas 
 * as <strong>diárias e taxas</strong> de saúde.
 * <p>
 * Centraliza os endpoints referentes ao cadastro, consulta, atualização e remoção
 * de <strong>diárias e taxas</strong> no sistema através do caminho base {@code /dth}.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see DthService
 * @see OcsService
 */
@Controller
@RequestMapping("/dth")
public class DthController {

  private static final Logger logger = LoggerFactory.getLogger(DthController.class);

  @Autowired
  private DthService dthSvc;

  @Autowired
  private OcsService ocsSvc;

  public DthController() {
    super();
    logger.debug("{} loaded", DthController.class.getSimpleName());
  }

  @ModelAttribute("allDth")
  public List<Dth> allDth() {
    return dthSvc.listAll();
  }

  @ModelAttribute("allOcs")
  public Map<Integer, String> allOcs() {
    return ocsSvc.mapAll();
  }

  @ModelAttribute("allUnidadeMedida")
  public List<UnidadeMedidaEnum> allUnidadeMedida() {
    return Arrays.asList(UnidadeMedidaEnum.values());
  }

  @ModelAttribute("tipoUmMap")
  public Map<String, String> tipoUmMap() {
    return Arrays.stream(UnidadeMedidaEnum.values()).collect(Collectors.toMap(UnidadeMedidaEnum::getCodigo, UnidadeMedidaEnum::getDescricao));
  }

  @GetMapping
  public String show(Integer id, Model model) {
    logger.info("+++ DTH +++");
    model.addAttribute("menu", "Dth");
    model.addAttribute("dth", id == null ? new Dth() : dthSvc.findById(id).orElse(new Dth()));
    return "dth";
  }

  @GetMapping({"/delete"})
  public String delete(Integer id) {
    dthSvc.remove(id);
    return "redirect:/dth";
  }

  @PostMapping(value = "/save", params = "save")
  public String save(@Valid Dth dth, BindingResult bindingResult, Model model) {
    if (dth.getOcs().getId() == null) {
      bindingResult.rejectValue("ocs.id", "dth.erro.ocs", "Deve ser informado");
    }
    else {
      ocsSvc.findById(dth.getOcs().getId()).ifPresentOrElse(o -> dth.setOcs(o) , () -> bindingResult.rejectValue("ocs.cnpj", "dth.erro.ocs", "Deve ser informado"));
    }
    if (bindingResult.hasErrors()) {
      return "dth";
    }
    
    logger.info("{}", dth);
    dthSvc.add(dth);
    
    return "redirect:/dth";
  }

}
