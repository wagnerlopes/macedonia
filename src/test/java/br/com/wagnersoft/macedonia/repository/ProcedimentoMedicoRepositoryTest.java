package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;

@DataJpaTest
class ProcedimentoMedicoRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProcedimentoMedicoRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar ProcedimentoMedico")
  void devePersistirProcedimentoMedico() {
    
    // Arrange
    ProcedimentoMedico pm = new ProcedimentoMedico();
    pm.setId(1);
    pm.setDescricao("X");

    // Act
    //ProcedimentoMedico salvo = entityManager.persistAndFlush(pm);
    ProcedimentoMedico salvo = repository.save(pm);

    // Assert
    assertThat(salvo.getId()).isEqualTo(1);
    assertThat(salvo.getDescricao()).isEqualTo("X");
  }

  @Test
  @DisplayName("Não deve persistir Beneficiario sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    ProcedimentoMedico pmInvalido = new ProcedimentoMedico();
    // Não definindo campos obrigatórios: cpf, nome e dt nasc
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(pmInvalido); });
  }

}
