package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.wagnersoft.macedonia.model.Profissional;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class Procedimento {

  private Integer sequencial;

  private String codigoProcedimento;

  private String descricaoProcedimento;

  private String tabela;

  private Integer quantidade;

  private String unidadeMedida;

  private BigDecimal valorUnitario;

  private BigDecimal valorTotal;

  private Profissional profissionalExecutante;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
  private LocalDate dataRealizacao;

  private Boolean procedimentoPrincipal;

}
