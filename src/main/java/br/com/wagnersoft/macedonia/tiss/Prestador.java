package br.com.wagnersoft.macedonia.tiss;

import lombok.Builder;
import lombok.Getter;

/**
 * Prestador. 
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Getter
@Builder
public class Prestador {

  private String numeroRegistroANSPrestador;

  private String nomePrestador;

  private String cnpj;

}
