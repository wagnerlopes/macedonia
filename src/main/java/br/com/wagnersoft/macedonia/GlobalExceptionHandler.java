package br.com.wagnersoft.macedonia;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Ponto central de controle de exceções na aplicação. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public String handleAll(Exception ex, Model model) {
    model.addAttribute("emsg", ex.getMessage());
    return "configuracoes";
  }

}
