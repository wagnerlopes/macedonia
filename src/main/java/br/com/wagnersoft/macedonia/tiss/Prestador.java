package br.com.wagnersoft.macedonia.tiss;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Prestador {

  private String numeroRegistroANSPrestador;

  private String nomePrestador;

  private String cnpj;

}
