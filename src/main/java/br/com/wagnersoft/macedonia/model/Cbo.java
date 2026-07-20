package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** 
 * Cbo Entity.
 * 
 * <p> Classificação Brasileira de Ocupacões (CBO) usada nas especialidades médicas dos {@link Profissional profissionais de saúde}.</p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Cbo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String codigo;

  private String descricao;

}
