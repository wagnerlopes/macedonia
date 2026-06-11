package br.com.wagnersoft.macedonia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.service.ContratoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import jakarta.validation.Valid;

/** Contrato Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
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

    @ModelAttribute("allContratos")
    public List<Contrato> listContratos() {
        return cttSvc.listAll();
    }
    
    @ModelAttribute("allOcs")
    public Map<String, String> listEstabelecimento() {
    	final Map<String, String> lista = new HashMap<>();
    	ocsSvc.listAll().forEach(e -> lista.put(e.getId().toString(), e.getDescricao()));
    	return lista;
    }
    
    @GetMapping({"/contratos"})
    public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
		logger.info("+++ Contratos +++");
		model.addAttribute("menu", "Contrato");
        model.addAttribute("contrato", id == null ? new Contrato() : cttSvc.findById(id));
        return "contratos";
    }

    @GetMapping({"/contratos/delete"})
    public String delete(@RequestParam(name = "id", required = false) Integer id) {
        cttSvc.remove(id);
        return "redirect:/contratos";
    }
    
    @PostMapping(value="/contratos/save", params={"save"})
    public String save(@Valid final Contrato contrato, final BindingResult bindingResult, final ModelMap model) {
    	if (contrato.getOcs().getId() == null)
    		bindingResult.rejectValue("ocs.id", "contrato.erro.ocs", "Deve ser informado");
    	else
          ocsSvc.findById(contrato.getOcs().getId()).ifPresentOrElse(o -> contrato.setOcs(o), () -> bindingResult.rejectValue("ocs.id", "contrato.erro.ocs", "Deve ser informado"));
        if (bindingResult.hasErrors()) {
        	return "contratos";
        }
        logger.info("{}", contrato);
        cttSvc.add(contrato);
        model.clear();
        return "redirect:/contratos";
    }
    
}
