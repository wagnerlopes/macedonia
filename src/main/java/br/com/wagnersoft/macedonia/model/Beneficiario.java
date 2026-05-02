package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Beneficiario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String cpf;

	private String nome;

	@Column(name="nascimento_data")
	private LocalDate nascimentoData;

	@OneToMany(mappedBy="beneficiario", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<GuiaEncaminhamento> guias;

}
