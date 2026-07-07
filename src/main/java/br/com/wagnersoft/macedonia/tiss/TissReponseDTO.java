package br.com.wagnersoft.macedonia.tiss;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** Troca de Informações de Saúde Complementar (TISS). */
@Getter
@Builder
@ToString
public class TissReponseDTO {

  private GuiaFaturamento guiaFaturamento;

}
