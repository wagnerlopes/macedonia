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

import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.ProfissionalService;

@Controller
public class ProfissionalController {

	private static final Logger logger = LoggerFactory.getLogger(ProfissionalController.class);

    @Autowired
    private ProfissionalService profSvc;
	
    public ProfissionalController() {
        super();
    }

    @ModelAttribute("allProfissionais")
    public List<Profissional> listProfissionais() {
    	return profSvc.listAll();
    }
    
	@GetMapping("/profissionais")
	public String profissionais(Model model) {
		logger.info("+++ Profissionais +++");
		model.addAttribute("menu", "prof");
		return "profissionais";
	}
    
    @PostMapping(value="/profissionais/save", params={"save"})
    public String save(final Profissional prof, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "profissionais";
        }
        logger.info("{}", prof);
        profSvc.add(prof);
        model.clear();
        return "redirect:/profissionais";
    }
    
}
