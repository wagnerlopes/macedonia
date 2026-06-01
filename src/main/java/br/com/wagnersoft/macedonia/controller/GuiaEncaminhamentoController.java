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

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import jakarta.validation.Valid;

@Controller
public class GuiaEncaminhamentoController {

	private static final Logger logger = LoggerFactory.getLogger(GuiaEncaminhamentoController.class);

    @Autowired
    private GuiaEncaminhamentoService guiaSvc;

    @Autowired
    private BeneficiarioService benSvc;

    @Autowired
    private ProfissionalService profSvc;

    @Autowired
    private OcsService ocsSvc;
    
    public GuiaEncaminhamentoController() {
        super();
    }

    @ModelAttribute("allGuias")
    public List<GuiaEncaminhamento> listGuias() {
    	return guiaSvc.listAll();
    }

    @ModelAttribute("allBeneficiario")
    public List<Beneficiario> listBeneficiario() {
    	return guiaSvc.allBeneficiario();
    }

    @ModelAttribute("allProfissional")
    public List<Profissional> listProfissional() {
    	return guiaSvc.allProfissional();
    }

    @ModelAttribute("allOcs")
    public List<Ocs> listOcs() {
    	return guiaSvc.allOcs();
    }
    
	@GetMapping("/guias")
    public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
		logger.info("+++ Guias +++");
		model.addAttribute("menu", "guias");
        model.addAttribute("guiaEncaminhamento", id == null ? new GuiaEncaminhamento() : guiaSvc.findById(id).orElse(new GuiaEncaminhamento()));
		return "guias";
	}
    
    @PostMapping(value="/guias/save", params={"save"})
    public String save(@Valid final GuiaEncaminhamento guiaEncaminhamento, final BindingResult bindingResult, final ModelMap model) {
        benSvc.findByCpf(guiaEncaminhamento.getBeneficiario().getCpf()).ifPresentOrElse(b -> guiaEncaminhamento.setBeneficiario(b) , () -> bindingResult.rejectValue("beneficiario", "", "Beneficiário deve ser informado"));
        profSvc.findByCpf(guiaEncaminhamento.getSolicitante().getCpf()).ifPresentOrElse(s -> guiaEncaminhamento.setSolicitante(s) , () -> bindingResult.rejectValue("solicitante", "", "Solicitante deve ser informado"));
        profSvc.findByCpf(guiaEncaminhamento.getResponsavel().getCpf()).ifPresentOrElse(r -> guiaEncaminhamento.setResponsavel(r) , () -> bindingResult.rejectValue("responsavel", "", "Responsável deve ser informado"));
        ocsSvc.findByCnpj(guiaEncaminhamento.getOcs().getCnpj()).ifPresentOrElse(o -> guiaEncaminhamento.setOcs(o) , () -> bindingResult.rejectValue("ocs", "", "OCS deve ser informado"));
        if (bindingResult.hasErrors()) {
        	return "guias";
        }
        logger.info("{}", guiaEncaminhamento);
        guiaSvc.add(guiaEncaminhamento);
        model.clear();
        return "redirect:/guias";
    }
    
}
