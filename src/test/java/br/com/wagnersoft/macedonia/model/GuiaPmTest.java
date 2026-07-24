package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuiaPmTest {

  private GuiaPm gpm;

  @BeforeEach
  void setUp() {
    gpm = new GuiaPm();
    gpm.setId(1);
  }
  
  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    GuiaEncaminhamento guia = new GuiaEncaminhamento();
    ProcedimentoMedico pm = new ProcedimentoMedico();
    
    BigDecimal valor = BigDecimal.valueOf(100.98);

    gpm.setGuiaEncaminhamento(guia);
    gpm.setPm(pm);
    gpm.setPmQtd(1);
    gpm.setPosAuditoria(valor);
    gpm.setUnidadeMedida("teste");
    gpm.setValorTotal(valor);
    gpm.setValorUnitario(valor);
    
    assertEquals(1, gpm.getId());
    assertEquals(1, gpm.getPmQtd());
    assertEquals(guia, gpm.getGuiaEncaminhamento());
    assertEquals(pm, gpm.getPm());
    assertEquals(valor, gpm.getPosAuditoria());
    assertEquals(valor, gpm.getValorTotal());
    assertEquals("teste", gpm.getUnidadeMedida());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {
    // Arrange
    GuiaPm gpmIgual = new GuiaPm();
    gpmIgual.setId(1);

    GuiaPm gpmDiferente = new GuiaPm();
    gpmDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(gpm, gpm);
    assertEquals(gpm, gpmIgual);
    assertEquals(gpm.hashCode(), gpmIgual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(gpm, gpmDiferente);
    assertNotEquals(gpm.hashCode(), gpmDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, gpm);
    assertNotEquals("Uma String qualquer", gpm);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = gpm.toString();
    assertTrue(toStringResult.contains("GuiaPm"));
    assertTrue(toStringResult.contains("id=1"));
  }
    
}
