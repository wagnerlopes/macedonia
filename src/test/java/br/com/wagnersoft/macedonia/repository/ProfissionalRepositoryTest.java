package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Profissional;

@DataJpaTest
class ProfissionalRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProfissionalRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar Profissional")
  void devePersistirProfissional() {
    // Arrange
    Profissional profissional = new Profissional();
    profissional.setCpf("98765432100");
    profissional.setNome("Ana Maria");

    // Act
    //Profissional salvo = entityManager.persistAndFlush(profissional);
    Profissional salvo = repository.save(profissional);

    // Assert
    assertThat(salvo.getCpf()).isEqualTo("98765432100");
    assertThat(salvo.getNome()).isEqualTo("Ana Maria");
  }

  @Test
  @DisplayName("Não deve persistir Beneficiario sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Profissional profissionalInvalido = new Profissional();
    // Não definindo campos obrigatórios: cpf, nome e especialidade
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(profissionalInvalido); });
  }

}
