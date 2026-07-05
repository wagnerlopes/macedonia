package br.com.wagnersoft.macedonia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Protocolo Entity.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Protocolo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String assunto;

  private String destino;

  @Column(name = "doc_data")
  private LocalDate docData;

  @Column(name = "doc_nr")
  private String docNr;

  @Column(name = "doc_tipo")
  private String docTipo;

  private String observacao;

  private Integer status;

  private BigDecimal valor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ocs_id")
  private Ocs ocs;

  @OneToMany(mappedBy = "protocolo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<GuiaEncaminhamento> guias = new ArrayList<>();

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Protocolo other = (Protocolo) obj;
    return Objects.equals(id, other.id);
  }

  public GuiaEncaminhamento addGuia(final GuiaEncaminhamento guia) {
    if (!this.getGuias().contains(guia)) {
      this.getGuias().add(guia);
      guia.setProtocolo(this);
    }
    return guia;
  }

  public GuiaEncaminhamento removeGuia(final GuiaEncaminhamento guia) {
    getGuias().remove(guia);
    guia.setProtocolo(null);
    return guia;
  }

}
