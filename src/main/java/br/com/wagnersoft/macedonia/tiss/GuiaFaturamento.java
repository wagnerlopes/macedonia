package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@SuppressFBWarnings({"EI_EXPOSE_REP"})
public class GuiaFaturamento {

  private final Cabecalho cabecalho;

  private final List<Procedimento> procedimentos;

  private final Valores valores;

  private final List<FormaPagamento> formasPagamento;

  private final String observacoes;

  //Construtor defensivo
  public GuiaFaturamento(Cabecalho cabecalho, List<Procedimento> procedimentos, Valores valores, List<FormaPagamento> formasPagamento, String observacoes) {
    this.cabecalho = cabecalho;
    this.procedimentos = List.copyOf(procedimentos);
    this.valores = valores;
    this.formasPagamento = List.copyOf(formasPagamento);
    this.observacoes = observacoes;
  }
 
  public static GuiaFaturamento empty() {
    return GuiaFaturamento.builder()
        .cabecalho(Cabecalho.builder().build())
        .procedimentos(List.of())
        .valores(Valores.builder()
            .valorTotalGlosa(BigDecimal.ZERO)
            .valorTotalBruto(BigDecimal.ZERO)
            .valorTotalLiquido(BigDecimal.ZERO.setScale(2))
            .descontos(BigDecimal.ZERO)
            .build())
        .formasPagamento(List.of())
        .observacoes("")
        .build();
  }

}
