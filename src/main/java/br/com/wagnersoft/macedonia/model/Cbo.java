package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Classificação Brasileira de Ocupacões. */
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
