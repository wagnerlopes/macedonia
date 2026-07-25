package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 
 * Registro Profissional do profissional de saúde.
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "registro_profissional")
public class RegistroProfissional implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  @Include
  private Integer id;

  private String conselho;

  private String numero;

  private String uf;

  public String toString() {
    return conselho + "-" + uf + " " + numero;
  }

}
