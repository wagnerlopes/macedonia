package br.com.wagnersoft.macedonia.type;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Valores {

	private BigDecimal valorTotalGlosa;
	
	private BigDecimal valorTotalBruto;
	
	private BigDecimal valorTotalLiquido;
	
	private BigDecimal descontos;
	
}
