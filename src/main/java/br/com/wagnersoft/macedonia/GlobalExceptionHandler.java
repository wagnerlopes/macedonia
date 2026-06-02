package br.com.wagnersoft.macedonia;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handleAll(Exception ex, Model model) {
		model.addAttribute("emsg", ex.getMessage());
		return "configuracoes";
	}

}
