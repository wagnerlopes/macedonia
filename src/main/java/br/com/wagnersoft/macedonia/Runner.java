package br.com.wagnersoft.macedonia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		logger.info("+++ Index +++");
		session.setMaxInactiveInterval(0);
		session.setAttribute("usr", " Meu Truta ");
		model.addAttribute("menu", "index");
		return "index";
	}
	
	@GetMapping("/configuracoes")
	public String configuracoes(Model model) {
		logger.info("+++ Config +++");
		model.addAttribute("menu", "config");
		return "configuracoes";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
