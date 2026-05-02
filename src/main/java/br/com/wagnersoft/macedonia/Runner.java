package br.com.wagnersoft.macedonia;

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

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private EspecialidadeService espSvc;

	@Autowired
	private BeneficiarioService benSvc;

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
		model.addAttribute("listBen", benSvc.listar());
		return "index";
	}

	@GetMapping("/contratos")
	public String contratos(Model model) {
		logger.info("+++ Contratos ++");
		model.addAttribute("menu", "cont");
		return "index";
	}

	@GetMapping("/especialidades")
	public String especialidades(Model model) {
		logger.info("+++ Especialidades ++");
		model.addAttribute("menu", "esp");
		model.addAttribute("listEsp", espSvc.listar());
		return "index";
	}

	@GetMapping("/profissionais")
	public String profissionais(Model model) {
		logger.info("+++ Profissionais ++");
		model.addAttribute("menu", "prof");
		return "index";
	}

	@GetMapping("/guias")
	public String guias(Model model) {
		logger.info("+++ Guias ++");
		model.addAttribute("menu", "guias");
		return "index";
	}

	@GetMapping("/ocs")
	public String ocs(Model model) {
		logger.info("+++ ocs ++");
		model.addAttribute("menu", "ocs");
		return "index";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
