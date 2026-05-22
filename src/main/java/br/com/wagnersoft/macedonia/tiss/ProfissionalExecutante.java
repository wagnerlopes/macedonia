package br.com.wagnersoft.macedonia.tiss;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfissionalExecutante {

	private Prestador identificacaoPrestador;
	
	private Operadora identificacaoOperadora;

    private LocalDate dataEmissao;

}
