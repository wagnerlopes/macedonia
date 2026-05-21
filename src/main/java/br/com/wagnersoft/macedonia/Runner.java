package br.com.wagnersoft.macedonia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.wagnersoft.macedonia.service.ProfissionalService;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private ProfissionalService profSvc;

	@GetMapping("/")
	public String index(Model model) {
		logger.info("+++ Index +++");
		model.addAttribute("menu", "index");
		model.addAttribute("usr", " Meu Truta ");
		return "index";
	}
	
	@GetMapping("/configuracoes")
	public String configuracoes(Model model) {
		logger.info("+++ Config +++");
		model.addAttribute("menu", "config");
		return "configuracoes";
	}

	@GetMapping("/especialidades")
	public String especialidades(Model model) {
		logger.info("+++ Especialidades +++");
		model.addAttribute("menu", "esp");
		model.addAttribute("lista", profSvc.listAllCBO());
		return "especialidades";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
