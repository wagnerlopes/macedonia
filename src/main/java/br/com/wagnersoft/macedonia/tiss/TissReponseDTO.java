package br.com.wagnersoft.macedonia.tiss;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Troca de Informações de Saúde Complementar (TISS). 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Getter
@Builder
@ToString
public class TissReponseDTO {

  private GuiaFaturamento guiaFaturamento;

}
