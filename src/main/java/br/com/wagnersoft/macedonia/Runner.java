package br.com.wagnersoft.macedonia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.wagnersoft.macedonia.model.Acomodacao;
import br.com.wagnersoft.macedonia.service.EspecialidadeService;

@Controller
@SpringBootApplication
public class Runner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private EspecialidadeService espSvc;

	@Autowired
	private MessageSource msg;

	@GetMapping("/")
	public String index(Model model) {
		logger.info("+++ Index +++");
		model.addAttribute("menu", "index");
		model.addAttribute("welcome", msg.getMessage("user.welcome", new Object[] {" Usuário "}, Locale.getDefault()));
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
		model.addAttribute("lista", espSvc.listAll());
		return "especialidades";
	}

	@GetMapping("/tiss")
	public String tiss(Model model) {
		logger.info("+++ TISS +++");
		model.addAttribute("menu", "tiss");
		model.addAttribute("lista", Collections.EMPTY_LIST);
		return "tiss";
	}

	@ModelAttribute("allAcomodacao")
    public List<Acomodacao> populateAcomodacao() {
        return Arrays.asList(Acomodacao.ALL);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
