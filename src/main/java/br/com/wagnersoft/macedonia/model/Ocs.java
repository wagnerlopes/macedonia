package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Include;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Ocs implements Comparable<Ocs>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@Include
	private String cnpj;

	private String registroAns;

	@NonNull
	@Include
	private String descricao;

	@NonNull
	private String especialidade;

	private String endereco;

	private String numero;

	private String complemento;

	private String municipio;

	private String uf;

	private String telefone;

	private String contato;

	@OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Contrato> contratos = new ArrayList<>();

	@OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Dth> dth = new ArrayList<>();

	@OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<GuiaEncaminhamento> guias = new ArrayList<>();

	@OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<OcsPm> ocsPm = new ArrayList<>();

	@Override
	public int compareTo(Ocs o) {
		return this.getDescricao().compareTo(o.getDescricao());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Ocs other = (Ocs) obj;
		return Objects.equals(id, other.id);
	}
	
}

/*
	public void addContrato(final Contrato c) throws Exception {
		if (this.getContratos() == null) {
			this.setContratos(new ArrayList<Contrato>(1));
		}
		if (this.getContratos().contains(c)) {
			throw new Exception("Contrato ja existe");
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

    public String getCnpjf() {
      final String CNPJ = "^\\d{3}.?\\d{3}.?\\d{3}/?\\d{3}-?\\d{2}$";
      return this.cnpj.isEmpty() ? "CNPJ" : new StringBuilder(cnpj.substring(0,2)).append(".")
                                                  .append(cnpj.substring(2,5)).append(".")
                                                  .append(cnpj.substring(5,8)).append("/")
                                                  .append(cnpj.substring(8,12)).append("-")
                                                  .append(cnpj.substring(12)).toString();
    }
*/
