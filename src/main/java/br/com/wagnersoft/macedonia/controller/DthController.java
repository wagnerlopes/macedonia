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
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.DthService;
import br.com.wagnersoft.macedonia.service.OcsService;
import jakarta.validation.Valid;

@Controller
public class DthController {

	private static final Logger logger = LoggerFactory.getLogger(DthController.class);

    @Autowired
    private DthService dthSvc;

    @Autowired
    private OcsService ocsSvc;
	
    public DthController() {
        super();
    }

    @ModelAttribute("allDth")
    public List<Dth> populateDth() {
        return dthSvc.listAll();
    }
    
    @ModelAttribute("allOcs")
    public List<Ocs> populateOcs() {
        return ocsSvc.listAll();
    }
    
    @GetMapping({"/dth"})
    public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
		logger.info("+++ DTH +++");
		model.addAttribute("menu", "Dth");
        model.addAttribute("dth", id == null ? new Dth() : dthSvc.findById(id));
        return "dth";
    }

    @GetMapping({"/dth/delete"})
    public String delete(@RequestParam(name = "id", required = false) Integer id) {
        dthSvc.remove(id);
        return "redirect:/dth";
    }
    
    @PostMapping(value="/dth/save", params={"save"})
    public String save(@Valid final Dth dth, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "dth";
        }
        final Ocs ocs = ocsSvc.findByCnpj(dth.getOcs().getCnpj()).orElse(new Ocs());
        dth.setOcs(ocs);
        logger.info("{}", dth);
        dthSvc.add(dth);
        model.clear();
        return "redirect:/dth";
    }
    
}
