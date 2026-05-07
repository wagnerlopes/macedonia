package br.com.wagnersoft.macedonia;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.EspecialidadeService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private EspecialidadeService espSvc;

	@Autowired
	private BeneficiarioService benSvc;

	@Autowired
	private OcsService ocsSvc;

	@Autowired
	private ProfissionalService profSvc;

	@GetMapping("/")
	public String dashboard(Model model) {
		logger.info("+++ Index ++");
		model.addAttribute("menu", "index");
		return "index";
	}
	
	@GetMapping("/beneficiarios")
	public String beneficiarios(Model model) {
		logger.info("+++ Beneficiarios ++");
		model.addAttribute("menu", "ben");
		model.addAttribute("lista", benSvc.listar());
		return "beneficiarios";
	}

	@GetMapping("/contratos")
	public String contratos(Model model) {
		logger.info("+++ Contratos ++");
		model.addAttribute("menu", "cont");
		model.addAttribute("lista", Collections.EMPTY_LIST);
		return "contratos";
	}

	@GetMapping("/especialidades")
	public String especialidades(Model model) {
		logger.info("+++ Especialidades ++");
		model.addAttribute("menu", "esp");
		model.addAttribute("lista", espSvc.listar());
		return "especialidades";
	}

	@GetMapping("/profissionais")
	public String profissionais(Model model) {
		logger.info("+++ Profissionais ++");
		model.addAttribute("menu", "prof");
		model.addAttribute("lista", profSvc.listar());
		return "profissionais";
	}

	@GetMapping("/guias")
	public String guias(Model model) {
		logger.info("+++ Guias ++");
		model.addAttribute("menu", "guias");
		model.addAttribute("lista", Collections.EMPTY_LIST);
		return "guias";
	}

	@GetMapping("/ocs")
	public String ocs(Model model) {
		logger.info("+++ ocs ++");
		model.addAttribute("menu", "ocs");
		model.addAttribute("lista", ocsSvc.listar());
		return "ocs";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
