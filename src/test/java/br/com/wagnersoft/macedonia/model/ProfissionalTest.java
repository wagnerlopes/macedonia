package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfissionalTest {

  private Profissional profissional;

  @BeforeEach
  void setUp() {
    profissional = new Profissional();
    profissional.setCpf("12345678901");
    profissional.setNome("Wagner Lopes");
  }

  @Test
  @DisplayName("Deve gerar getter e setter corretamente")
  void testGettersAndSetters() {

    Cbo cbo = new Cbo();
    RegistroProfissional rp = new RegistroProfissional();

    profissional.setCbo(cbo);
    profissional.setCns("12345");
    profissional.setRegistroProfissional(rp);

    assertEquals("12345678901", profissional.getCpf());
    assertEquals("Wagner Lopes", profissional.getNome());
    assertEquals(cbo, profissional.getCbo());
    assertEquals("12345", profissional.getCns());
    assertEquals(rp, profissional.getRegistroProfissional());

  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no CPF")
  void deveRespeitarEqualsEHashCodeBaseadoNoCpf() {

    Profissional outro = new Profissional();
    outro.setCpf("12345678901");

    assertThat(profissional).isEqualTo(outro);
    assertThat(profissional.hashCode()).isEqualTo(outro.hashCode());
  }


  @Test
  @DisplayName("Deve adicionar e remover procedimento mantendo relacionamento bidirecional")
  void deveAdicionarRemoverGuiaMantendoRelacionamentoBidirecional() {

    GuiaEncaminhamento guia = new GuiaEncaminhamento(); 

    profissional.addGuiaSolicitante(guia);

    assertAll(
        () -> assertThat(profissional.getGuiasSolicitante()).contains(guia),
        () -> assertThat(guia.getSolicitante()).isEqualTo(profissional)
        );

    profissional.removeGuiaSolicitante(guia);

    assertAll(
        () -> assertThat(profissional.getGuiasSolicitante()).isEmpty(),
        () -> assertThat(guia.getSolicitante()).isNull()
        );

    profissional.addGuiaResponsavel(guia);

    assertAll(
        () -> assertThat(profissional.getGuiasResponsavel()).contains(guia),
        () -> assertThat(guia.getResponsavel()).isEqualTo(profissional)
        );

    profissional.removeGuiaResponsavel(guia);

    assertAll(
        () -> assertThat(profissional.getGuiasResponsavel()).isEmpty(),
        () -> assertThat(guia.getResponsavel()).isNull()
        );

  }

}
