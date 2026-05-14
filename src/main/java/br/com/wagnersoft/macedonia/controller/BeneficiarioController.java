package br.com.wagnersoft.macedonia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import jakarta.validation.Valid;

@Controller
public class BeneficiarioController {

	private static final Logger logger = LoggerFactory.getLogger(BeneficiarioController.class);

    @Autowired
    private BeneficiarioService benSvc;

    public BeneficiarioController() {
        super();
    }
    
    @GetMapping({"/beneficiarios"})
    public String show(final Beneficiario beneficiario, Model model) {
		logger.info("+++ Beneficiarios +++");
		model.addAttribute("menu", "ben");
		model.addAttribute("lista", benSvc.listAll());
        return "beneficiarios";
    }
    
    @PostMapping(value="/beneficiarios/save", params={"save"})
    public String save(@Valid final Beneficiario beneficiario, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/beneficiarios";
        }
        logger.info("{}", beneficiario);
        benSvc.add(beneficiario);
        model.clear();
        return "redirect:/beneficiarios";
    }
    
}
