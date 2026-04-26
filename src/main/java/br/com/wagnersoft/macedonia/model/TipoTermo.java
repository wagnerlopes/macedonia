package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="tipo_termo")
public class TipoTermo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private int codigo;

  private String descricao;

}
