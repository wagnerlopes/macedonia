package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Protocolo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String assunto;

	private LocalDate data;

	private String destino;

	@Column(name="doc_data")
	private LocalDate docData;

	@Column(name="doc_nr")
	private String docNr;

	@Column(name="doc_tipo")
	private String docTipo;

	private String observacao;

	private Integer status;

	@ManyToOne
	@JoinColumn(name="ocs_id")
	private Ocs ocs;

	private BigDecimal valor;

	@OneToMany(mappedBy="protocolo")
	private List<GuiaEncaminhamento> guias;

	public Protocolo(final Integer id) {
		this();
		this.id = id;
	}

}