package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Protocolo;

@DataJpaTest
class ProtocoloRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProtocoloRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar Protocolo")
  void devePersistirProtocolo() {
    LocalDate data = LocalDate.of(2020, 1, 1);

    // Arrange
    Protocolo protocolo = new Protocolo();
    protocolo.setAssunto("A");
    protocolo.setDocData(data);

    // Act
    //Protocolo salvo = entityManager.persistAndFlush(protocolo);
    Protocolo salvo = repository.save(protocolo);

    // Assert
    assertThat(salvo.getAssunto()).isEqualTo("A");
    assertThat(salvo.getDocData()).isEqualTo(data);
  }

  @Test
  @DisplayName("Não deve persistir Beneficiario sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Protocolo protocololInvalido = new Protocolo();
    // Não definindo campos obrigatórios: id, assunto e data
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(protocololInvalido); });
  }

}
