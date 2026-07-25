package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Ocs;

@DataJpaTest
class OcsRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private OcsRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar Ocs")
  void devePersistirOcs() {
    // Arrange
    Ocs ocs = new Ocs();
    ocs.setId(1);

    // Act
    //Ocs salvo = entityManager.persistAndFlush(ocs);
    Ocs salvo = repository.save(ocs);

    // Assert
    assertThat(salvo.getId()).isEqualTo(1);
  }

  @Test
  @DisplayName("Não deve persistir Ocs sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Ocs ocsInvalida = new Ocs();
    // Não definindo campos obrigatórios: id, nr e dt emissao
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(ocsInvalida); });
  }

}
