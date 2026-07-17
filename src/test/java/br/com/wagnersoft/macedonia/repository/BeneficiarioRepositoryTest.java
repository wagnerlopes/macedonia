package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Beneficiario;

@DataJpaTest
class BeneficiarioRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BeneficiarioRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar Beneficiario")
  void devePersistirBeneficiario() {
    // Arrange
    Beneficiario beneficiario = new Beneficiario();
    beneficiario.setCpf("98765432100");
    beneficiario.setNome("Ana Maria");
    beneficiario.setNascimentoData(LocalDate.of(1990, 5, 15));
    
    // Act
    //Beneficiario salvo = entityManager.persistAndFlush(beneficiario);
    Beneficiario salvo = repository.save(beneficiario);
    
    // Assert
    assertThat(salvo.getCpf()).isEqualTo("98765432100");
    assertThat(salvo.getNome()).isEqualTo("Ana Maria");
  }

  @Test
  @DisplayName("Não deve persistir Beneficiario sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Beneficiario beneficiarioInvalido = new Beneficiario();
    // Não definindo campos obrigatórios: cpf, nome e dt nasc
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(beneficiarioInvalido); });
  }
  
}
