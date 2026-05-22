package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FormaPagamento {

	private String tipo;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
	private LocalDate dataPagamento;
	
	private BigDecimal valorPago;

}
