package br.com.wagnersoft.macedonia.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.wagnersoft.macedonia.controller.GuiaEncaminhamentoController;

/** 
 * Chama o Builder que carrega os ModelAtributes da View Guias.
 * A opção de carregar diretamento no Controller os ModelAttibutes foi
 * descartada porque o mesmo código era chamado em SearchController e
 * GuiaEncaminhamentoController.
 *
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@ControllerAdvice(assignableTypes = GuiaEncaminhamentoController.class)
public class GuiaEncaminhamentoViewModelAdvice {

  @Autowired GuiaEncaminhamentoViewModelBuilder builder;

  @ModelAttribute
  public void addGuiaModel(Model model) {
    builder.populateGuias(model);
  }

}