package br.com.wagnersoft.macedonia.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

  @ModelAttribute("usuarioLogado")
  public String usuarioLogado() {
    return "XYZ";
  }

}
