package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Include;

/** 
 * Organização conveniada de saúde.
 * 
 * <p>São os Estabelecimentos de Saúde conveniados cadastrados
 *  para atendimento dos {@link Beneficiario Beneficiarios}.
 *  
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@SuppressFBWarnings({"EI_EXPOSE_REP"})
public class Ocs implements Comparable<Ocs>, Serializable {

  private static final long serialVersionUID = 1L;

  //@NotNull
  @Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Include
  private String cnpj;

  @NotBlank
  @Include
  private String descricao;

  @NotBlank
  private String especialidade;

  private String registroAns;

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
  private List<Dth> dths = new ArrayList<>();

  @OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<GuiaEncaminhamento> guias = new ArrayList<>();

  @OneToMany(mappedBy = "ocs", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OcsPm> procedimentos = new ArrayList<>();

  public Ocs(Integer id) {
    super();
    this.id = id;
  }

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

  public Contrato addContrato(final Contrato contrato) {
    if (!this.getContratos().contains(contrato)) {
      this.getContratos().add(contrato);
      contrato.setOcs(this);
    }
    return contrato;
  }

  public Contrato removeContrato(final Contrato contrato) {
    getContratos().remove(contrato);
    contrato.setOcs(null);
    return contrato;
  }

  public Dth addDth(final Dth dth) {
    if (!this.getDths().contains(dth)) {
      this.getDths().add(dth);
      dth.setOcs(this);
    }
    return dth;
  }

  public Dth removeDth(final Dth dth) {
    getDths().remove(dth);
    dth.setOcs(null);
    return dth;
  }

  public GuiaEncaminhamento addGuia(final GuiaEncaminhamento guia) {
    if (!this.getGuias().contains(guia)) {
      this.getGuias().add(guia);
      guia.setOcs(this);
    }
    return guia;
  }

  public GuiaEncaminhamento removeGuia(final GuiaEncaminhamento guia) {
    getGuias().remove(guia);
    guia.setOcs(null);
    return guia;
  }

  public OcsPm addOcsPm(final OcsPm opm) {
    if (this.getProcedimentos().contains(opm)) {
      this.getProcedimentos().remove(opm);
    }
    this.getProcedimentos().add(opm);
    opm.setOcs(this);
    return opm;
  }

  public OcsPm removeOcsPm(final OcsPm opm) {
    getProcedimentos().remove(opm);
    opm.setOcs(null);
    return opm;
  }

}
