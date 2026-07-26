package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BeneficiarioTest {

  private Beneficiario beneficiario;

  @BeforeEach
  void setUp() {
    beneficiario = new Beneficiario();
    beneficiario.setCpf("12345678901");
    beneficiario.setNome("Wagner Lopes");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {
    assertEquals("12345678901", beneficiario.getCpf());
    assertEquals("Wagner Lopes", beneficiario.getNome());
  }

  @Test
  @DisplayName("Deve calcular idade corretamente")
  void deveCalcularIdadeCorretamente() {
    // Se hoje for 16/07/2026, alguém nascido em 16/07/1996 tem exatamente 30 anos.
    beneficiario.setNascimentoData(LocalDate.now().minusYears(30));
    assertEquals(beneficiario.getIdade(), 30);
  }

  @Test
  @DisplayName("Deve retornar idade 0 quando data de nascimento nula")
  void deveRetornarZeroQuandoDataNascimentoForNula() {
    beneficiario.setNascimentoData(null);
    assertEquals(beneficiario.getIdade(), 0);
  }

  @Test
  @DisplayName("Deve retornar idade -1 quando data nascimento no futuro")
  void deveRetornarMenosUmQuandoDataNascimentoFutura() {
    beneficiario.setNascimentoData(LocalDate.now().plusDays(1));
    assertEquals(beneficiario.getIdade(), -1);
  }

  @Test
  @DisplayName("Deve calcular faixa etária corretamente")
  void deveCalcularFaixaEtariaCorretamente() {
    // Exemplo: 25 anos -> faixa "20 a 30"
    beneficiario.setNascimentoData(LocalDate.now().minusYears(25));
    assertEquals(beneficiario.getFaixaEtaria(), "20 a 30");

    // Exemplo de borda: 30 anos (30 % 10 == 0 -> s = 29 -> "20 a 30")
    beneficiario.setNascimentoData(LocalDate.now().minusYears(30));
    assertEquals(beneficiario.getFaixaEtaria(), "20 a 30");

    // Exemplo de borda: 31 anos -> faixa "30 a 40"
    beneficiario.setNascimentoData(LocalDate.now().minusYears(31));
    assertEquals(beneficiario.getFaixaEtaria(), "30 a 40");

    // Exemplo de borda: 0 anos -> faixa "0 a 10"
    beneficiario.setNascimentoData(LocalDate.now());
    assertEquals(beneficiario.getFaixaEtaria(), "0 a 10");
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no CPF")
  void testEqualsAndHashCode() {

    // Arrange
    Beneficiario igual = new Beneficiario();
    igual.setCpf("12345678901");

    Beneficiario diferente = new Beneficiario();
    diferente.setCpf("11111111111");

    // Teste de igualdade (mesmo ID)
    assertTrue(beneficiario.equals(beneficiario));
    assertTrue(beneficiario.equals(igual));
    assertEquals(beneficiario.hashCode(), igual.hashCode());

    // Teste de diferença (IDs diferentes)
    assertFalse(beneficiario.equals(diferente));
    assertNotEquals(beneficiario.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(beneficiario.equals(null));
    assertNotEquals(beneficiario, "Uma String qualquer");

  }

  @Test
  @DisplayName("Deve adicionar e remover procedimento mantendo relacionamento bidirecional")
  void deveAdicionarRemoverGuiaMantendoRelacionamentoBidirecional() {
    GuiaEncaminhamento guia = new GuiaEncaminhamento(); 

    beneficiario.addGuia(guia);

    assertAll(
        () -> assertTrue(beneficiario.getGuias().contains(guia)),
        () -> assertEquals(guia.getBeneficiario(), beneficiario)
        );

    assertTrue(beneficiario.addGuia(guia).equals(guia));

    beneficiario.removeGuia(guia);

    assertAll(
        () -> assertTrue(beneficiario.getGuias().isEmpty()),
        () -> assertNull(guia.getBeneficiario())
        );
  }
  
}
