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

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;

/** Procedimento Medico Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Controller
public class ProcedimentoMedicoController {

	private static final Logger logger = LoggerFactory.getLogger(ProcedimentoMedicoController.class);

    @Autowired
    private ProcedimentoMedicoService pmSvc;
	
    public ProcedimentoMedicoController() {
        super();
    	logger.debug("{} loaded", ProcedimentoMedicoController.class.getSimpleName());
    }

    @ModelAttribute("allProcedimentos")
    public List<ProcedimentoMedico> listProcedimentos() {
    	return pmSvc.listAll();
    }
    
	@GetMapping("/procedimentos")
	public String procedimentos(Model model) {
		logger.info("+++ Procedimentos +++");
		model.addAttribute("menu", "proc");
		return "procedimentos";
	}
    
    @PostMapping(value="/procedimentos", params={"save"})
    public String save(final ProcedimentoMedico pm, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "procedimentos";
        }
        logger.info("{}", pm);
        pmSvc.add(pm);
        model.clear();
        return "redirect:/procedimentos";
    }
    
}
