package br.com.wagnersoft.macedonia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.wagnersoft.macedonia.service.CboService;

/** 
 * Controller Spring MVC responsável por gerenciar as requisições relacionadas as <strong>especialidades de saúde</strong>.
 * <p>
 * Centraliza os endpoints referentes a consulta de <strong>especialidades de saúde</strong> no sistema através
 * do caminho base {@code /especialidades}.
 * </p>
 * <p>As <strong>especialidades de saúde</strong> são as profissões da <i>Classificação Brasileira de Ocupações (CBO)</i>
 * referentes especificamente a área de saúde.
 * </p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 * @see CboService
 */
@Controller
@RequestMapping("/especialidades")
public class EspecialidadeController {

  private static final Logger logger = LoggerFactory.getLogger(EspecialidadeController.class);

  @Autowired
  private CboService cboSvc;

  public EspecialidadeController() {
    super();
    logger.debug("{} loaded", EspecialidadeController.class.getSimpleName());
  }

  @GetMapping
  public String especialidades(Model model) {
    logger.info("+++ Especialidades +++");
    model.addAttribute("menu", "esp");
    model.addAttribute("lista", cboSvc.listAll());
    return "especialidades";
  }

}
