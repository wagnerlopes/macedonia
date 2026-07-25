package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    Ocs ocs = new Ocs();
    Profissional solicitante = new Profissional();
    Profissional responsavel = new Profissional();
    Beneficiario beneficiario = new Beneficiario();
    Protocolo protocolo = new Protocolo();

    BigDecimal valor = BigDecimal.valueOf(100.98);
    LocalDate now = LocalDate.now();

    guia.setOperador("Teste 1");
    guia.setObservacao("Diaria");
    guia.setValorTotal(valor);
    guia.setEmissaoData(now);
    guia.setOcs(ocs);
    guia.setSolicitante(solicitante);
    guia.setResponsavel(responsavel);
    guia.setBeneficiario(beneficiario);
    guia.setProtocolo(protocolo);

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
    GuiaEncaminhamento guiaIgual = new GuiaEncaminhamento();
    guiaIgual.setId(1);

    GuiaEncaminhamento guiaDiferente = new GuiaEncaminhamento();
    guiaDiferente.setId(2);

    // Teste de igualdade (mesmo ID)
    assertEquals(guia, guia);
    assertEquals(guia, guiaIgual);
    assertEquals(guia.hashCode(), guiaIgual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertNotEquals(guia, guiaDiferente);
    assertNotEquals(guia.hashCode(), guiaDiferente.hashCode());

    // Limite: nulo e classes diferentes
    assertNotEquals(null, guia);
    assertNotEquals("Uma String qualquer", guia);
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
    GuiaPm procedimento = new GuiaPm(); 
    procedimento.setId(1);

    guia.addGuiaPm(procedimento);

    assertAll(
        () -> assertThat(guia.getProcedimentos()).contains(procedimento),
        () -> assertThat(procedimento.getGuiaEncaminhamento()).isEqualTo(guia)
        );

    guia.removeGuiaPm(procedimento);

    assertAll(
        () -> assertThat(guia.getProcedimentos()).isEmpty(),
        () -> assertThat(procedimento.getGuiaEncaminhamento()).isNull()
        );
  }

}
