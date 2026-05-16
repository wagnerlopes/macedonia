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

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;

@Controller
public class GuiaEncaminhamentoController {

	private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoController.class);

    @Autowired
    private GuiaEncaminhamentoService guiaSvc;
	
    public GuiaEncaminhamentoController() {
        super();
    }

    @ModelAttribute("allGuias")
    public List<GuiaEncaminhamento> listGuias() {
    	return guiaSvc.listAll();
    }
    
	@GetMapping("/guias")
	public String guias(Model model) {
		logger.info("+++ Guias +++");
		model.addAttribute("menu", "guias");
		return "guias";
	}
    
    @PostMapping(value="/guias/save", params={"save"})
    public String save(final GuiaEncaminhamento guia, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "guias";
        }
        logger.info("{}", guia);
        guiaSvc.add(guia);
        model.clear();
        return "redirect:/guias";
    }
    
}
