package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcedimentoMedicoTest {

  private ProcedimentoMedico pm;

  @BeforeEach
  void setUp() {
    pm = new ProcedimentoMedico();
    pm.setId(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    pm.setChQtd(1);
    pm.setAmb90("90");
    pm.setAmb92("92");
    pm.setAmb96("96");
    pm.setAmb99("99");
    pm.setTuss("TUSS");
    pm.setGrupo("G");
    pm.setSubgrupo("S");
    pm.setDescricao("D");
    pm.setAuxiliaresQtd(1);
    pm.setPorteAnestesico(1);
    
    assertEquals(1, pm.getId());
    assertEquals("90", pm.getAmb90());
    assertEquals("92", pm.getAmb92());
    assertEquals("96", pm.getAmb96());
    assertEquals("99", pm.getAmb99());
    assertEquals("TUSS", pm.getTuss());
    assertEquals("G", pm.getGrupo());
    assertEquals("S", pm.getSubgrupo());
    assertEquals("D", pm.getDescricao());
    assertEquals(1, pm.getAuxiliaresQtd());
    assertEquals(1, pm.getPorteAnestesico());
    
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {

    // Arrange
    ProcedimentoMedico pmIgual = new ProcedimentoMedico();
    pmIgual.setId(1);

    ProcedimentoMedico pmDiferente = new ProcedimentoMedico();
    pmDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(pm, pm);
    assertEquals(pm, pmIgual);
    assertEquals(pm.hashCode(), pmIgual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(pm, pmDiferente);
    assertNotEquals(pm.hashCode(), pmDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, pm);
    assertNotEquals("Uma String qualquer", pm);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = pm.toString();
    assertTrue(toStringResult.contains("ProcedimentoMedico"));
    assertTrue(toStringResult.contains("id=1"));
  }

}
