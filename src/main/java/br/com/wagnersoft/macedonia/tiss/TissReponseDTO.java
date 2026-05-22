package br.com.wagnersoft.macedonia.tiss;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** Troca de Informações de Saúde Complementar (TISS). */
@Getter
@Builder
@ToString
public class TissReponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuiaFaturamento guiaFaturamento;

}
