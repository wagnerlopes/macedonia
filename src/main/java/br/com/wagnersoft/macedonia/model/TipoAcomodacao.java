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
@Table(name="tipo_acomodacao")
public class TipoAcomodacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String codigo;

	private String descricao;

}