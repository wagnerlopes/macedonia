package br.com.wagnersoft.macedonia.repository; 

import static org.assertj.core.api.Assertions.assertThat;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
class BeneficiarioRepositoryTest {

  //@Autowired
  //private TestEntityManager entityManager;

  @Autowired
  private BeneficiarioRepository repository;

  @Test
  void devePersistirERecuperarBeneficiario() {
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
}
