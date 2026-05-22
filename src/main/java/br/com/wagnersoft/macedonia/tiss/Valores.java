package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Valores {

	private BigDecimal valorTotalGlosa;
	
	private BigDecimal valorTotalBruto;
	
	private BigDecimal valorTotalLiquido;
	
	private BigDecimal descontos;
	
}
