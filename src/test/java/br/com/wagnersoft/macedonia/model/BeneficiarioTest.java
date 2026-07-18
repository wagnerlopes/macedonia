package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
  @DisplayName("Deve calcular idade corretamente")
  void deveCalcularIdadeCorretamente() {
    // Se hoje for 16/07/2026, alguém nascido em 16/07/1996 tem exatamente 30 anos.
    beneficiario.setNascimentoData(LocalDate.now().minusYears(30));
    assertThat(beneficiario.getIdade()).isEqualTo(30);
  }

  @Test
  @DisplayName("Deve retornar idade 0 quando data de nascimento nula")
  void deveRetornarZeroQuandoDataNascimentoForNula() {
    beneficiario.setNascimentoData(null);
    assertThat(beneficiario.getIdade()).isZero();
  }

  @Test
  @DisplayName("Deve retornar idade -1 quando data nascimento no futuro")
  void deveRetornarMenosUmQuandoDataNascimentoFutura() {
    beneficiario.setNascimentoData(LocalDate.now().plusDays(1));
    assertThat(beneficiario.getIdade()).isEqualTo(-1);
  }

  @Test
  @DisplayName("Deve calcular faixa etária corretamente")
  void deveCalcularFaixaEtariaCorretamente() {
    // Exemplo: 25 anos -> faixa "20 a 30"
    beneficiario.setNascimentoData(LocalDate.now().minusYears(25));
    assertThat(beneficiario.getFaixaEtaria()).isEqualTo("20 a 30");

    // Exemplo de borda: 30 anos (30 % 10 == 0 -> s = 29 -> "20 a 30")
    beneficiario.setNascimentoData(LocalDate.now().minusYears(30));
    assertThat(beneficiario.getFaixaEtaria()).isEqualTo("20 a 30");

    // Exemplo de borda: 31 anos -> faixa "30 a 40"
    beneficiario.setNascimentoData(LocalDate.now().minusYears(31));
    assertThat(beneficiario.getFaixaEtaria()).isEqualTo("30 a 40");
  }

  @Test
  @DisplayName("Deve adicionar e remover procedimento mantendo relacionamento bidirecional")
  void deveAdicionarRemoverGuiaMantendoRelacionamentoBidirecional() {
    GuiaEncaminhamento guia = new GuiaEncaminhamento(); 

    beneficiario.addGuia(guia);

    assertAll(
        () -> assertThat(beneficiario.getGuias()).contains(guia),
        () -> assertThat(guia.getBeneficiario()).isEqualTo(beneficiario)
        );

    beneficiario.removeGuia(guia);

    assertAll(
        () -> assertThat(beneficiario.getGuias()).isEmpty(),
        () -> assertThat(guia.getBeneficiario()).isNull()
        );
  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no CPF")
  void deveRespeitarEqualsEHashCodeBaseadoNoCpf() {
    Beneficiario b1 = new Beneficiario();
    b1.setCpf("12345678901");

    Beneficiario b2 = new Beneficiario();
    b2.setCpf("12345678901");

    assertThat(b1).isEqualTo(b2);
    assertThat(b1.hashCode()).isEqualTo(b2.hashCode());
  }

}
