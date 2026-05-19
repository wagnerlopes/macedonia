package br.com.wagnersoft.macedonia.type;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuiaFaturamento {

	private Cabecalho cabecalho;
	
	private List<Procedimento> procedimentos;
	
	private Valores valores;
	
	private List<FormaPagamento> formasPagamento;
	
	private String observacoes;

}
