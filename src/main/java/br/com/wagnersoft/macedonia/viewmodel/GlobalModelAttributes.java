package br.com.wagnersoft.macedonia.viewmodel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.wagnersoft.macedonia.type.UfEnum;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;

@ControllerAdvice
public class GlobalModelAttributes {

  @ModelAttribute("usrAuth")
  public String usrAuth() {
    return "Meu Truta";
  }

  @ModelAttribute("allUf")
  public List<UfEnum> allUf() {
    return Arrays.asList(UfEnum.values());
  }

  @ModelAttribute("unidadeMedidaMap")
  public Map<String, String> unidadeMedidaMap() {
    return Arrays.stream(UnidadeMedidaEnum.values()).collect(Collectors.toMap(UnidadeMedidaEnum::getCodigo, UnidadeMedidaEnum::getDescricao));
  }
  
}
