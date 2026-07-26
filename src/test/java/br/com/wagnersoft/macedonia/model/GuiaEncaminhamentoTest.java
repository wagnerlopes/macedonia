package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuiaEncaminhamentoTest {

  private GuiaEncaminhamento guia;

  @BeforeEach
  void setUp() {
    guia = new GuiaEncaminhamento();
    guia.setId(1);
    guia.setGuiaNr(1);
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    // Arrange
    Ocs ocs = new Ocs();
    Beneficiario beneficiario = new Beneficiario();
    Protocolo protocolo = new Protocolo();
    Profissional responsavel = new Profissional();
    Profissional solicitante = new Profissional();

    BigDecimal valor = BigDecimal.valueOf(100.98);
    LocalDate now = LocalDate.now();

    // Act
    guia.setOperador("Teste 1");
    guia.setObservacao("Diaria");
    guia.setValorTotal(valor);
    guia.setEmissaoData(now);
    guia.setOcs(ocs);
    guia.setBeneficiario(beneficiario);;
    guia.setProtocolo(protocolo);
    guia.setResponsavel(responsavel);
    guia.setSolicitante(solicitante);

    // Assert
    assertEquals(1, guia.getId());
    assertEquals(1, guia.getGuiaNr());
    assertEquals("Teste 1", guia.getOperador());
    assertEquals("Diaria", guia.getObservacao());
    assertEquals(valor, guia.getValorTotal());
    assertEquals(now, guia.getEmissaoData());
    assertEquals(ocs, guia.getOcs());
    assertEquals(solicitante, guia.getSolicitante());
    assertEquals(responsavel, guia.getResponsavel());
    assertEquals(beneficiario, guia.getBeneficiario());
    assertEquals(protocolo, guia.getProtocolo());
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no ID")
  void testEqualsAndHashCode() {
    // Arrange
    GuiaEncaminhamento igual = new GuiaEncaminhamento();
    igual.setId(1);

    GuiaEncaminhamento diferente = new GuiaEncaminhamento();
    diferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertTrue(guia.equals(guia));
    assertTrue(guia.equals(igual));
    assertEquals(guia.hashCode(), igual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertFalse(guia.equals(diferente));
    assertNotEquals(guia.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(guia.equals(null));
    assertNotEquals(guia, "Uma String qualquer");
  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    String toStringResult = guia.toString();
    assertTrue(toStringResult.contains("GuiaEncaminhamento"));
    assertTrue(toStringResult.contains("id=1"));
  }

  @Test
  @DisplayName("Deve adicionar e remover procedimento mantendo relacionamento bidirecional")
  void deveAdicionarRemoverProcedimentoMantendoRelacionamentoBidirecional() {
    // Arrange
    GuiaPm procedimento = new GuiaPm(); 
    procedimento.setId(1);

    // Act
    guia.addGuiaPm(procedimento);

    // Assert
    assertAll(
        () -> assertTrue(guia.getProcedimentos().contains(procedimento)),
        () -> assertEquals(procedimento.getGuiaEncaminhamento(), guia)
        );

    assertTrue(guia.addGuiaPm(procedimento).equals(procedimento));

    guia.removeGuiaPm(procedimento);

  }

}
