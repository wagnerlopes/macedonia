package br.com.wagnersoft.macedonia.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/** Guia de Encaminhamento Controller.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
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
    	return benSvc.listAll();
    }

    @ModelAttribute("allProfissional")
    public List<Profissional> listProfissional() {
    	return profSvc.listAll();
    }

    @ModelAttribute("allOcs")
    public List<Ocs> listOcs() {
    	return ocsSvc.listAll();
    }

	@ModelAttribute("unidadeMedidaMap")
    public Map<String, String> unidadeMedidaMap() {
        return Arrays.stream(UnidadeMedidaEnum.values()).collect(Collectors.toMap(UnidadeMedidaEnum::getCodigo, UnidadeMedidaEnum::getDescricao));
    }

	@GetMapping("/guias")
    public String show(@RequestParam(name = "id", required = false) Integer id, Model model) {
		logger.info("+++ Guias +++");
		model.addAttribute("menu", "guias");
		final GuiaEncaminhamento guia = id == null ? new GuiaEncaminhamento() : guiaSvc.findById(id).orElse(new GuiaEncaminhamento());
        model.addAttribute("guiaEncaminhamento", guia);
        model.addAttribute("opm", this.listOcsPm(guia));
		return "guias";
	}
    
    @RequestMapping(value="/guias", params={"save"})
    public String save(@Valid final GuiaEncaminhamento guiaEncaminhamento, final BindingResult bindingResult, final ModelMap model) {
        benSvc.findByCpf(guiaEncaminhamento.getBeneficiario().getCpf()).ifPresentOrElse(b -> guiaEncaminhamento.setBeneficiario(b) , () -> bindingResult.rejectValue("beneficiario.cpf", "guia.erro.beneficiario", "Deve ser informado"));
        ocsSvc.findById(guiaEncaminhamento.getOcs().getId()).ifPresentOrElse(o -> guiaEncaminhamento.setOcs(o) , () -> bindingResult.rejectValue("ocs.id", "guia.erro.ocs", "Deve ser informado"));
        profSvc.findByCpf(guiaEncaminhamento.getSolicitante().getCpf()).ifPresentOrElse(s -> guiaEncaminhamento.setSolicitante(s) , () -> bindingResult.rejectValue("solicitante.cpf", "guia.erro.solicitante", "Deve ser informado"));
        profSvc.findByCpf(guiaEncaminhamento.getResponsavel().getCpf()).ifPresentOrElse(r -> guiaEncaminhamento.setResponsavel(r) , () -> bindingResult.rejectValue("responsavel.cpf", "guia.erro.responsavel", "Deve ser informado"));
        if (bindingResult.hasErrors()) {
        	return "guias";
        }
        logger.info("NEW = {}", guiaEncaminhamento);
        guiaSvc.add(guiaEncaminhamento);
        model.clear();
        return "redirect:/guias";
    }

    @RequestMapping(value="/guias", params={"addRow"})
    public String addRow(final GuiaEncaminhamento guiaEncaminhamento, final BindingResult bindingResult, final Model model) {
        guiaEncaminhamento.getProcedimentos().add(new GuiaPm());
        ocsSvc.findById(guiaEncaminhamento.getOcs().getId()).ifPresentOrElse(o -> guiaEncaminhamento.setOcs(o) , () -> bindingResult.rejectValue("ocs.id", "guia.erro.ocs", "Deve ser informado"));
        model.addAttribute("guia", guiaEncaminhamento);
        model.addAttribute("opm", this.listOcsPm(guiaEncaminhamento));
        return "guias";
    }

    @RequestMapping(value="/guias", params={"removeRow"})
    public String removeRow(final GuiaEncaminhamento guiaEncaminhamento, final BindingResult bindingResult, final HttpServletRequest req, final Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        guiaEncaminhamento.getProcedimentos().remove(rowId.intValue());
        ocsSvc.findById(guiaEncaminhamento.getOcs().getId()).ifPresentOrElse(o -> guiaEncaminhamento.setOcs(o) , () -> bindingResult.rejectValue("ocs.id", "guia.erro.ocs", "Deve ser informado"));
        model.addAttribute("guia", guiaEncaminhamento);
        model.addAttribute("opm", this.listOcsPm(guiaEncaminhamento));
        return "guias";
    }

    private List<?> listOcsPm(final GuiaEncaminhamento guiaEncaminhamento) {
    	return guiaEncaminhamento.getOcs() != null ? guiaEncaminhamento.getOcs().getProcedimentos() : Collections.EMPTY_LIST;
    }


}
