package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OcsPmTest {

  private OcsPm opm;

  @BeforeEach
  void setUp() {
    opm = new OcsPm();
    opm.setId(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    Ocs ocs = new Ocs();
    ProcedimentoMedico pm = new ProcedimentoMedico();

    opm.setChQtd(1);
    opm.setOcs(ocs);
    opm.setPm(pm);
    opm.setValorUnitario(BigDecimal.ONE);
    opm.setUnidadeMedida("X");

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
    OcsPm opmIgual = new OcsPm();
    opmIgual.setId(1);

    OcsPm opmDiferente = new OcsPm();
    opmDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(opm, opm);
    assertEquals(opm, opmIgual);
    assertEquals(opm.hashCode(), opmIgual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(opm, opmDiferente);
    assertNotEquals(opm.hashCode(), opmDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, opm);
    assertNotEquals("Uma String qualquer", opm);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = opm.toString();
    assertTrue(toStringResult.contains("OcsPm"));
    assertTrue(toStringResult.contains("id=1"));
  }

}
