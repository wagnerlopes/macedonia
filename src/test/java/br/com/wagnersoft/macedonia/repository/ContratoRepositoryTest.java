package br.com.wagnersoft.macedonia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.model.Ocs;

@DataJpaTest // Configura um banco em memória e o EntityManager para o teste
class ContratoRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("Deve persistir e recuperar Contrato")
  void devePersistirContrato() {
    // Arrange
    Ocs ocs = new Ocs();
    ocs.setCnpj("123456789010001");
    ocs.setDescricao("XXX");
    ocs.setEspecialidade("ZZZ");
    Ocs persist = entityManager.persist(ocs);

    Contrato contrato = new Contrato();
    contrato.setInicioData(LocalDate.now());
    contrato.setTerminoData(LocalDate.now().plusMonths(6));
    contrato.setChQtd(40);
    contrato.setOcs(persist);

    // Act: salva e limpa o contexto para forçar a leitura do BD
    Contrato contratoSalvo = entityManager.persistAndFlush(contrato);
    entityManager.clear();
    
    assertThat(contratoSalvo.getId()).isNotNull();
    Contrato contratoBuscado = entityManager.find(Contrato.class, contratoSalvo.getId());

    // Assert
    assertThat(contratoBuscado.getChQtd()).isEqualTo(40);
    assertThat(contratoBuscado.getOcs()).isEqualTo(ocs);
  }

  @Test
  @DisplayName("Não deve persistir Contrato sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Contrato contratoInvalido = new Contrato();
    // Não definindo campos obrigatórios: inicioData, terminoData e ocs
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(contratoInvalido); });
  }

}
