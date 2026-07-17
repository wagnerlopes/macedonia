package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BeneficiarioValidationTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Não deve permitir CPF com menos de 11 dígitos")
  void naoDevePermitirCpfComMenosDeOnzeDigitos() {
    // Arrange
    Beneficiario beneficiario = new Beneficiario();
    beneficiario.setCpf("123"); // Inválido
    beneficiario.setNome("Nome Valido");
    beneficiario.setNascimentoData(LocalDate.now().minusYears(20));

    // Act
    Set<ConstraintViolation<Beneficiario>> violations = validator.validate(beneficiario);

    // Assert
    assertThat(violations).isNotEmpty();
    assertThat(violations.iterator().next().getMessage()).isEqualTo("informar 11 dígitos");
  }

  @Test
  @DisplayName("Não deve permitir Data de Nascimento no futuro")
  void naoDevePermitirDataDeNascimentoNoFuturo() {
    // Arrange
    Beneficiario beneficiario = new Beneficiario();
    beneficiario.setCpf("12345678901");
    beneficiario.setNome("Nome Valido");
    beneficiario.setNascimentoData(LocalDate.now().plusDays(1)); // Inválido (@Past)

    // Act
    Set<ConstraintViolation<Beneficiario>> violations = validator.validate(beneficiario);

    // Assert
    assertThat(violations).isNotEmpty();
  }
}
