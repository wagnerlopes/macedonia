package br.com.wagnersoft.macedonia.type;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Prestador {

	private String numeroRegistroANSPrestador;
	
	private String nomePrestador;
	
	private String cnpj;
	
}
