package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OcsTest {

  private Ocs ocs;

  @BeforeEach
  void setUp() {
    ocs = new Ocs(1);
    ocs.setDescricao("DES");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    // Arrange
    ocs.setCnpj("1");
    ocs.setComplemento("COM");
    ocs.setContato("CON");
    ocs.setEndereco("END");
    ocs.setEspecialidade("ESP");
    ocs.setMunicipio("MUN");
    ocs.setNumero("1");
    ocs.setRegistroAns("123");
    ocs.setTelefone("999");
    ocs.setUf("KK");

    // Assert
    assertEquals(1, ocs.getId());
    assertEquals("1", ocs.getCnpj());
    assertEquals("CON", ocs.getContato());
    assertEquals("DES", ocs.getDescricao());
    assertEquals("END", ocs.getEndereco());
    assertEquals("ESP", ocs.getEspecialidade());
    assertEquals("MUN", ocs.getMunicipio());
    assertEquals("1", ocs.getNumero());
    assertEquals("123", ocs.getRegistroAns());
    assertEquals("999", ocs.getTelefone());
    assertEquals("KK", ocs.getUf());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {
    // Arrange
    Ocs igual = new Ocs();
    igual.setId(1);
    igual.setDescricao("DES");

    Ocs diferente = new Ocs();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(ocs.equals(ocs));
    assertTrue(ocs.equals(igual));
    assertEquals(ocs.hashCode(), igual.hashCode());
    assertTrue(ocs.compareTo(igual) == 0);

    // Teste de diferença (IDs diferentes)
    assertFalse(ocs.equals(diferente));
    assertNotEquals(ocs.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(ocs.equals(null));
    assertNotEquals(ocs, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = ocs.toString();
    assertTrue(toStringResult.contains("Ocs"));
    assertTrue(toStringResult.contains("id=1"));
  }

  @Test
  @DisplayName("Deve adicionar e remover ocs mantendo relacionamento bidirecional")
  void deveAdicionarRemoverocsMantendoRelacionamentoBidirecional() {
    // Arrange
    Contrato contrato = new Contrato(); 
    contrato.setId(1);

    // Act
    ocs.addContrato(contrato);

    // Assert
    assertAll(
        () -> assertTrue(ocs.getContratos().contains(contrato)),
        () -> assertEquals(contrato.getOcs(), ocs)
        );

    assertTrue(ocs.addContrato(contrato).equals(contrato));

    ocs.removeContrato(contrato);

    // Arrange
    Dth dth = new Dth(); 
    dth.setId(1);

    // Act
    ocs.addDth(dth);

    // Assert
    assertAll(
        () -> assertTrue(ocs.getDths().contains(dth)),
        () -> assertEquals(dth.getOcs(), ocs)
        );

    assertTrue(ocs.addDth(dth).equals(dth));

    ocs.removeDth(dth);

    // Arrange
    GuiaEncaminhamento guia = new GuiaEncaminhamento(); 
    guia.setId(1);

    // Act
    ocs.addGuia(guia);

    // Assert
    assertAll(
        () -> assertTrue(ocs.getGuias().contains(guia)),
        () -> assertEquals(guia.getOcs(), ocs)
        );

    assertTrue(ocs.addGuia(guia).equals(guia));

    ocs.removeGuia(guia);

    // Arrange
    OcsPm opm = new OcsPm(); 
    opm.setId(1);

    // Act
    ocs.addOcsPm(opm);

    // Assert
    assertAll(
        () -> assertTrue(ocs.getProcedimentos().contains(opm)),
        () -> assertEquals(opm.getOcs(), ocs)
        );

    assertTrue(ocs.addOcsPm(opm).equals(opm));

    ocs.removeOcsPm(opm);

  }

}
