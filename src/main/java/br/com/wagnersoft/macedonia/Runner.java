package br.com.wagnersoft.macedonia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.wagnersoft.macedonia.service.EspecialidadeService;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private EspecialidadeService svc;

	@GetMapping("/")
	public String home(Model model) {
		logger.info("+++ New user ++");
		model.addAttribute("name", "User");
		model.addAttribute("listEsp", svc.listar());
		return "home";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
