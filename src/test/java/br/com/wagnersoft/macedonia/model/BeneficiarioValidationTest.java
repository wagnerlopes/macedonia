package br.com.wagnersoft.macedonia.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeneficiarioValidationTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void naoDevePermitirCpfComMenosDeOnzeDigitos() {
    Beneficiario beneficiario = new Beneficiario();
    beneficiario.setCpf("123"); // Inválido
    beneficiario.setNome("Nome Valido");
    beneficiario.setNascimentoData(LocalDate.now().minusYears(20));

    Set<ConstraintViolation<Beneficiario>> violations = validator.validate(beneficiario);

    assertThat(violations).isNotEmpty();
    assertThat(violations.iterator().next().getMessage()).isEqualTo("informar 11 dígitos");
  }

  @Test
  void naoDevePermitirDataDeNascimentoNoFuturo() {
    Beneficiario beneficiario = new Beneficiario();
    beneficiario.setCpf("12345678901");
    beneficiario.setNome("Nome Valido");
    beneficiario.setNascimentoData(LocalDate.now().plusDays(1)); // Inválido (@Past)

    Set<ConstraintViolation<Beneficiario>> violations = validator.validate(beneficiario);

    assertThat(violations).isNotEmpty();
  }
}
