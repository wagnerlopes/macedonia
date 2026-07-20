package br.com.wagnersoft.macedonia.tiss;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Valores. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Getter
@Setter
@Builder
public class Valores {

  @JsonProperty(defaultValue = "0")
  private BigDecimal valorTotalGlosa;

  @JsonProperty(defaultValue = "0")
  private BigDecimal valorTotalBruto;

  @JsonProperty(defaultValue = "0")
  private BigDecimal valorTotalLiquido;

  @JsonProperty(defaultValue = "0")
  private BigDecimal descontos;

}
