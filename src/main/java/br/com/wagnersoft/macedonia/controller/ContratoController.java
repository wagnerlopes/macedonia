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

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.ContratoService;
import br.com.wagnersoft.macedonia.service.OcsService;

@Controller
public class ContratoController {

	private static final Logger logger = LoggerFactory.getLogger(ContratoController.class);

    @Autowired
    private OcsService ocsSvc;
	
    @Autowired
    private ContratoService cttSvc;

    public ContratoController() {
        super();
    }

	@ModelAttribute("listOcs")
    public List<Ocs> listOcs() {
        return ocsSvc.listAll();
    }
    
    @GetMapping({"/contratos"})
    public String show(final Contrato contrato, Model model) {
		logger.info("+++ Contratos +++");
		model.addAttribute("menu", "Contrato");
		model.addAttribute("lista", cttSvc.listAll());
        return "contratos";
    }
    
    @PostMapping(value="/contratos/save", params={"save"})
    public String save(final Contrato contrato, final BindingResult bindingResult, final ModelMap model) {
        if (!bindingResult.hasErrors()) {
        	final Ocs ocs = ocsSvc.findByCnpj(contrato.getOcs().getCnpj());
        	contrato.setOcs(ocs);
        	logger.info("{}", contrato);
        	cttSvc.add(contrato);
        	model.clear();
        }
        return "redirect:/contratos";
    }
    
}
