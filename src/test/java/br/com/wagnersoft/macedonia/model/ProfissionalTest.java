package br.com.wagnersoft.macedonia.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    // Arrange
    Cbo cbo = new Cbo();
    RegistroProfissional rp = new RegistroProfissional();

    // Act
    profissional.setCbo(cbo);
    profissional.setCns("12345");
    profissional.setRegistroProfissional(rp);

    // Assert
    assertEquals("12345678901", profissional.getCpf());
    assertEquals("Wagner Lopes", profissional.getNome());
    assertEquals(cbo, profissional.getCbo());
    assertEquals("12345", profissional.getCns());
    assertEquals(rp, profissional.getRegistroProfissional());

  }

  @Test
  @DisplayName("Deve respeitar Equals e Hashcode no CPF")
  void deveRespeitarEqualsEHashCodeBaseadoNoCpf() {

    Profissional igual = new Profissional();
    igual.setCpf("12345678901");
    igual.setNome("Wagner Lopes");

    Profissional diferente = new Profissional();
    diferente.setCpf("11111111111");

    // Teste de igualdade (mesmo ID)
    assertTrue(profissional.equals(profissional));
    assertTrue(profissional.equals(igual));
    assertEquals(profissional.hashCode(), igual.hashCode());
    assertTrue(profissional.compareTo(igual) == 0);

    // Teste de diferença (IDs diferentes)
    assertFalse(profissional.equals(diferente));
    assertNotEquals(profissional.hashCode(), diferente.hashCode());

    // Limite: nulo e classes diferentes
    assertFalse(profissional.equals(null));
    assertNotEquals(profissional, "Uma String qualquer");

  }


  @Test
  @DisplayName("Deve adicionar e remover procedimento mantendo relacionamento bidirecional")
  void deveAdicionarRemoverGuiaMantendoRelacionamentoBidirecional() {

    GuiaEncaminhamento guia = new GuiaEncaminhamento(); 

    profissional.addGuiaSolicitante(guia);

    assertAll(
        () -> assertTrue(profissional.getGuiasSolicitante().contains(guia)),
        () -> assertEquals(guia.getSolicitante(), profissional)
        );

    assertTrue(profissional.addGuiaSolicitante(guia).equals(guia));

    profissional.removeGuiaSolicitante(guia);

    profissional.addGuiaResponsavel(guia);

    assertAll(
        () -> assertTrue(profissional.getGuiasResponsavel().contains(guia)),
        () -> assertEquals(guia.getResponsavel(), profissional)
        );

    assertTrue(profissional.addGuiaResponsavel(guia).equals(guia));

    profissional.removeGuiaResponsavel(guia);

  }

  @Test
  @DisplayName("Deve gerar toString corretamente")
  void testToString() {
    // Verifica se o toString do Lombok gera uma string contendo informações essenciais
    String toStringResult = profissional.toString();
    assertTrue(toStringResult.contains("Profissional"));
    assertTrue(toStringResult.contains("cpf=12345678901"));
  }

}
