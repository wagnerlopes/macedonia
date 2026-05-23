package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.type.EstabelecimentoSaudeEnum;
import br.com.wagnersoft.macedonia.type.UfEnum;
import jakarta.validation.Valid;

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

	@ModelAttribute("allEspecialidade")
    public List<EstabelecimentoSaudeEnum> allEspecialidade() {
        return Arrays.asList(EstabelecimentoSaudeEnum.ALL);
    }
    
	@ModelAttribute("allUf")
    public List<UfEnum> allUf() {
        return Arrays.asList(UfEnum.ALL);
    }

	@GetMapping("/ocs")
    public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
		logger.info("+++ OCS +++");
		model.addAttribute("menu", "ocs");
        model.addAttribute("ocs", id == null ? new Ocs() : ocsSvc.findById(id).orElse(new Ocs()));
        model.addAttribute("tipoOcsMap", Arrays.stream(EstabelecimentoSaudeEnum.values()).collect(Collectors.toMap(EstabelecimentoSaudeEnum::getCodigo, EstabelecimentoSaudeEnum::getDescricao)));
        return "ocs";
    }

    @GetMapping({"/ocs/delete"})
    public String delete(@RequestParam(name = "id", required = false) Integer id) {
        ocsSvc.remove(id);
        return "redirect:/ocs";
    }
	
    @PostMapping(value="/ocs/save", params={"save"})
    public String save(@Valid final Ocs ocs, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
        	return "ocs";
        }
        logger.info("{}", ocs);
        ocsSvc.add(ocs);
        model.clear();
        return "redirect:/ocs";
    }
    
}
