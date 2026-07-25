package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OcsTest {

  private Ocs ocs;

  @BeforeEach
  void setUp() {
    ocs = new Ocs();
    ocs.setId(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    ocs.setCnpj("1");
    ocs.setComplemento("COM");
    ocs.setContato("CON");
    ocs.setDescricao("DES");
    ocs.setEndereco("END");
    ocs.setEspecialidade("ESP");
    ocs.setMunicipio("MUN");
    ocs.setNumero("1");
    ocs.setRegistroAns("123");
    ocs.setTelefone("999");
    ocs.setUf("KK");

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
    Ocs ocsIgual = new Ocs();
    ocsIgual.setId(1);

    Ocs ocsDiferente = new Ocs();
    ocsDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(ocs, ocs);
    assertEquals(ocs, ocsIgual);
    assertEquals(ocs.hashCode(), ocsIgual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(ocs, ocsDiferente);
    assertNotEquals(ocs.hashCode(), ocsDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, ocs);
    assertNotEquals("Uma String qualquer", ocs);
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = ocs.toString();
    assertTrue(toStringResult.contains("Ocs"));
    assertTrue(toStringResult.contains("id=1"));
  }

  @Test
  @DisplayName("Deve adicionar e remover contrato mantendo relacionamento bidirecional")
  void deveAdicionarRemoverProcedimentoMantendoRelacionamentoBidirecional() {

    Contrato contrato = new Contrato(); 
    contrato.setId(1);

    ocs.addContrato(contrato);

    assertAll(
        () -> assertThat(ocs.getContratos()).contains(contrato),
        () -> assertThat(contrato.getOcs()).isEqualTo(ocs)
        );

    ocs.removeContrato(contrato);

    assertAll(
        () -> assertThat(ocs.getContratos()).isEmpty(),
        () -> assertThat(contrato.getOcs()).isNull()
        );
  }

}
