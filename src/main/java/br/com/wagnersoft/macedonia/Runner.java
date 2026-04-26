package br.com.wagnersoft.macedonia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Runner {

	@RequestMapping("/")
	String home() {
		return "Runnig...";
	}

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
