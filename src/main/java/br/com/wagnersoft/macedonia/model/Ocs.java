package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name="ocs")
@NamedQuery(name="Ocs.findByCnpj", query="SELECT o FROM Ocs o WHERE o.cnpj = ?1")
@NamedQuery(name="Ocs.findByEspec", query="SELECT o FROM Ocs o WHERE o.especialidade = ?1 ORDER BY o.descricao")
@NamedQuery(name="Ocs.excluir", query="DELETE FROM Ocs o")
public class Ocs implements Comparable<Ocs>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String cnpj;

	private String descricao;

	private String especialidade;

	private String endereco;

	private String numero;

	private String complemento;

	private String municipio;

	private String uf;

	private String telefone;

	private String contato;

	@OneToMany(mappedBy="ocs", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<Contrato> contratos;

	@OneToMany(mappedBy="ocs", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<OcsPm> ocsPm;

	@OneToMany(mappedBy="ocs", fetch=FetchType.EAGER, orphanRemoval=true)
	private List<Dth> dth;

	@OneToMany(mappedBy="ocs")
	private List<GuiaEncaminhamento> guias;

	public Ocs(final Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(Ocs o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

	public void addContrato(final Contrato c) throws Exception {
		if (this.getContratos() == null) {
			this.setContratos(new ArrayList<Contrato>(1));
		}
		if (this.getContratos().contains(c)) {
			throw new Exception("Contrato j� existe");
		}
		this.getContratos().add(c);
		if (c.getOcs() != this) {
			c.setOcs(this);
		}
	}

	public void addDth(final Dth d) throws Exception {
		if (this.getDth() == null) {
			this.setDth(new ArrayList<Dth>(1));
		}
		if (!this.getDth().contains(d)) {
			this.getDth().add(d);
			if (d.getOcs() != this) {
				d.setOcs(this);
			}
		}
	}

	/*
  public String getCnpjf() {
    //final String CNPJ = "^\\d{3}.?\\d{3}.?\\d{3}/?\\d{3}-?\\d{2}$";
    return this.cnpj.isEmpty() ? "CNPJ" : new StringBuilder(cnpj.substring(0,2)).append(".")
                                                    .append(cnpj.substring(2,5)).append(".")
                                                    .append(cnpj.substring(5,8)).append("/")
                                                    .append(cnpj.substring(8,12)).append("-")
                                                    .append(cnpj.substring(12)).toString();
  }
	 */

}
