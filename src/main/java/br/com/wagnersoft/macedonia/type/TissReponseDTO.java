package br.com.wagnersoft.macedonia.type;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** Troca de Informações de Saúde Complementar (TISS). */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TissReponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private GuiaFaturamento guiaFaturamento;

}
