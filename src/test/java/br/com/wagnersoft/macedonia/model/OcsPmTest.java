package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OcsPmTest {

  private OcsPm opm;

  private ProcedimentoMedico pm;

  private Ocs ocs;

  @BeforeEach
  void setUp() {
    opm = new OcsPm();
    ocs = new Ocs();
    pm = new ProcedimentoMedico();

    opm.setId(1);
    opm.setOcs(ocs);
    opm.setPm(pm);
    opm.getOcs().setDescricao("OCS");
    opm.getPm().setDescricao("PM");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    // Arrange

    // Act
    opm.setChQtd(1);
    opm.setOcs(ocs);
    opm.setPm(pm);
    opm.setValorUnitario(BigDecimal.ONE);
    opm.setUnidadeMedida("X");

    // Assert
    assertEquals(1, opm.getId());
    assertEquals(1, opm.getChQtd());
    assertEquals(ocs, opm.getOcs());
    assertEquals(pm, opm.getPm());
    assertEquals(BigDecimal.ONE, opm.getValorUnitario());
    assertEquals("X", opm.getUnidadeMedida());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {

    // Arrange
    OcsPm igual = new OcsPm();
    igual.setId(1);
    igual.setOcs(ocs);
    igual.setPm(pm);
    igual.getOcs().setDescricao("OCS");
    igual.getPm().setDescricao("PM");

    OcsPm diferente = new OcsPm();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(opm.equals(opm));
    assertTrue(opm.equals(igual));
    assertEquals(opm.hashCode(), igual.hashCode());
    assertTrue(opm.compareTo(igual) == 0);

    // Teste de diferença (IDs diferentes)
    assertFalse(opm.equals(diferente));
    assertNotEquals(opm.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(opm.equals(null));
    assertNotEquals(opm, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = opm.toString();
    assertTrue(toStringResult.contains("OcsPm"));
    assertTrue(toStringResult.contains("id=1"));
  }

}
