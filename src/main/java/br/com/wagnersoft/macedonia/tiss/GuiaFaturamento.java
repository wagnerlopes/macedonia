package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;
import java.util.Collections;
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

  public static GuiaFaturamento empty() {
    return GuiaFaturamento.builder()
        .cabecalho(Cabecalho.builder().build())
        .procedimentos(Collections.emptyList())
        .valores(Valores.builder()
            .valorTotalGlosa(BigDecimal.ZERO)
            .valorTotalBruto(BigDecimal.ZERO)
            .valorTotalLiquido(BigDecimal.ZERO.setScale(2))
            .descontos(BigDecimal.ZERO)
            .build())
        .formasPagamento(Collections.emptyList())
        .observacoes("")
        .build();
  }

}
