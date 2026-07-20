package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

/** 
 * Contrato Entity.
 * 
 * <p>Contratos com os {@link Ocs estabelecimentos de saúde}.</p>
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
@SuppressFBWarnings({"EI_EXPOSE_REP"})
public class Contrato implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "inicio_data")
  private LocalDate inicioData;

  @NotNull
  @Column(name = "termino_data")
  private LocalDate terminoData;

  @NotNull
  @Column(name = "ch_qtd")
  private int chQtd;

  @NotNull
  @Exclude
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "ocs_id", updatable = false, nullable = false)
  private Ocs ocs;

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Contrato other = (Contrato) obj;
    return Objects.equals(id, other.id);
  }

}
