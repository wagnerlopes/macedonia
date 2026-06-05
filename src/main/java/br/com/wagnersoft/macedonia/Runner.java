package br.com.wagnersoft.macedonia;

import java.time.Duration;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import br.com.wagnersoft.macedonia.service.CboService;
import jakarta.servlet.http.HttpSession;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private CboService cboSvc;

	@Bean
	public LocaleResolver localeResolver() {
	    final CookieLocaleResolver resolver = new CookieLocaleResolver("LOCALE");
	    resolver.setDefaultLocale(Locale.of("pt", "BR"));
	    resolver.setCookieMaxAge(Duration.ofSeconds(3600));
	    return resolver;
	}
	
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

	@GetMapping("/especialidades")
	public String especialidades(Model model) {
		logger.info("+++ Especialidades +++");
		model.addAttribute("menu", "esp");
		model.addAttribute("lista", cboSvc.listAllCBO());
		return "especialidades";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
