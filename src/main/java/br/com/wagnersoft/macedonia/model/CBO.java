package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;

	private String descricao;

}
