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

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.OcsService;

@Controller
public class OcsController {

	private static final Logger logger = LoggerFactory.getLogger(OcsController.class);

    @Autowired
    private OcsService ocsSvc;
	
    public OcsController() {
        super();
    }

    @ModelAttribute("allOcs")
    public List<Ocs> listOcs() {
    	return ocsSvc.listAll();
    }
    
	@GetMapping("/ocs")
	public String ocs(Ocs ocs, Model model) {
		logger.info("+++ OCS +++");
		model.addAttribute("menu", "ocs");
		model.addAttribute("lista", ocsSvc.listAll());
		return "ocs";
	}
    
    @PostMapping(value="/ocs/save", params={"save"})
    public String save(final Ocs ocs, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "ocs";
        }
        logger.info("{}", ocs);
        ocsSvc.add(ocs);
        model.clear();
        return "redirect:/ocs";
    }
    
}
