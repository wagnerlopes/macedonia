package br.com.wagnersoft.macedonia.tiss;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

/**
 * Cabecalho. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Getter
@Builder
public class Cabecalho {

  private Prestador identificacaoPrestador;

  private Operadora identificacaoOperadora;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
  private LocalDate dataEmissao;

}
