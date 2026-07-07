package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Beneficiario Entity.
 * <p>Os beneficiários são as pessoas que fazem uso das {@link GuiaEncaminhamento guias de encaminhamento} nos {@link Ocs estabelecimentos de saúde}.</p>
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Beneficiario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Size(min = 11, max = 11, message = "informar 11 dígitos")
  @NotBlank
  @Digits(integer = 11, fraction = 0, message = "somente dígitos permitidos")
  private String cpf;

  @NotBlank
  @Size(max = 255)
  private String nome;

  @NotNull
  @Past
  @Column(name = "nascimento_data")
  private LocalDate nascimentoData;

  @Getter(AccessLevel.NONE)
  @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<GuiaEncaminhamento> guias = new ArrayList<>();

  public List<GuiaEncaminhamento> getGuias() {
    return Collections.unmodifiableList(guias);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(cpf);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Beneficiario other = (Beneficiario) obj;
    return Objects.equals(cpf, other.cpf);
  }

  public long getIdade() {
    if (nascimentoData == null) return 0;
    final LocalDate today = LocalDate.now();
    if (nascimentoData.isAfter(today)) return -1;
    return ChronoUnit.YEARS.between(nascimentoData, today);
  }	

  public String getFaixaEtaria() {
    long idade = getIdade();
    long s = idade == 0 ? idade + 1 : idade % 10 == 0 ? idade - 1 : idade;
    return (s / 10) * 10 + " a " + ((s + 9) / 10) * 10;
  }

  public GuiaEncaminhamento addGuia(final GuiaEncaminhamento guia) {
    if (!this.guias.contains(guia)) {
      this.guias.add(guia);
      guia.setBeneficiario(this);
    }
    return guia;
  }

  public GuiaEncaminhamento removeGuia(final GuiaEncaminhamento guia) {
    this.guias.remove(guia);
    guia.setBeneficiario(null);
    return guia;
  }

}
