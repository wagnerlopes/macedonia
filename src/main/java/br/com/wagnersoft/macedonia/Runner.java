package br.com.wagnersoft.macedonia;

import java.util.Collections;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.wagnersoft.macedonia.service.ContratoService;
import br.com.wagnersoft.macedonia.service.EspecialidadeService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private EspecialidadeService espSvc;

	@Autowired
	private ContratoService cttSvc;

	@Autowired
	private GuiaEncaminhamentoService guiaSvc;

	@Autowired
	private OcsService ocsSvc;

	@Autowired
	private ProfissionalService profSvc;

	@Autowired
	private MessageSource msg;

	@GetMapping("/")
	public String dashboard(Model model) {
		logger.info("+++ Index +++");
		model.addAttribute("menu", "index");
		model.addAttribute("welcome", msg.getMessage("user.welcome", new Object[] {" Usuário "}, Locale.getDefault()));
		return "index";
	}
	
	@GetMapping("/config")
	public String config(Model model) {
		logger.info("+++ Config +++");
		model.addAttribute("menu", "config");
		return "configuracoes";
	}
	
	@GetMapping("/contratos")
	public String contratos(Model model) {
		logger.info("+++ Contratos +++");
		model.addAttribute("menu", "cont");
		model.addAttribute("lista", cttSvc.listar());
		return "contratos";
	}

	@GetMapping("/especialidades")
	public String especialidades(Model model) {
		logger.info("+++ Especialidades +++");
		model.addAttribute("menu", "esp");
		model.addAttribute("lista", espSvc.listar());
		return "especialidades";
	}

	@GetMapping("/profissionais")
	public String profissionais(Model model) {
		logger.info("+++ Profissionais +++");
		model.addAttribute("menu", "prof");
		model.addAttribute("lista", profSvc.listar());
		return "profissionais";
	}

	@GetMapping("/guias")
	public String guias(Model model) {
		logger.info("+++ Guias +++");
		model.addAttribute("menu", "guias");
		model.addAttribute("lista", guiaSvc.listar());
		return "guias";
	}

	@GetMapping("/ocs")
	public String ocs(Model model) {
		logger.info("+++ OCS +++");
		model.addAttribute("menu", "ocs");
		model.addAttribute("lista", ocsSvc.listar());
		return "ocs";
	}
	
	@GetMapping("/tiss")
	public String tiss(Model model) {
		logger.info("+++ TISS +++");
		model.addAttribute("menu", "tiss");
		model.addAttribute("lista", Collections.EMPTY_LIST);
		return "tiss";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
