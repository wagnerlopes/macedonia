package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

/** 
 * Profissional da área de saúde.
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressFBWarnings({"EI_EXPOSE_REP"})
public class Profissional implements Comparable<Profissional>, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @NotBlank
  private String cpf;

  @NotBlank
  private String nome;

  private String cns;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "cbo_codigo")
  private Cbo cbo;

  @NotNull
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "registro_id")
  private RegistroProfissional registroProfissional;

  @JsonIgnore
  @Exclude
  @OneToMany(mappedBy = "solicitante", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private List<GuiaEncaminhamento> guiasSolicitante = new ArrayList<>();

  @JsonIgnore
  @Exclude
  @OneToMany(mappedBy = "responsavel", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private List<GuiaEncaminhamento> guiasResponsavel = new ArrayList<>();

  @Override
  public int compareTo(Profissional o) {
    return this.getNome().compareTo(o.getNome());
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpf);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Profissional other = (Profissional) obj;
    return Objects.equals(cpf, other.cpf);
  }

}
