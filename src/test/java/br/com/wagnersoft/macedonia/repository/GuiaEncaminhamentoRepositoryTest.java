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
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;

@DataJpaTest
class GuiaEncaminhamentoRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private GuiaEncaminhamentoRepository repository;

  @Test
  @DisplayName("Deve persistir e recuperar GuiaEncaminhamento")
  void devePersistirGuiaEncaminhamento() {
    // Arrange
    GuiaEncaminhamento guia = new GuiaEncaminhamento();
    guia.setId(1);
    guia.setGuiaNr(1);
    guia.setEmissaoData(LocalDate.of(2026, 5, 1));
    guia.setSolicitante(new Profissional());
    guia.setResponsavel(new Profissional());
    guia.setBeneficiario(new Beneficiario());
    guia.setOcs(new Ocs());
    
    // Act
    //GuiaEncaminhamento salvo = entityManager.persistAndFlush(guia);
    GuiaEncaminhamento salvo = repository.save(guia);
    
    // Assert
    assertThat(salvo.getId()).isEqualTo(1);
    assertThat(salvo.getGuiaNr()).isEqualTo(1);
  }

  @Test
  @DisplayName("Não deve persistir GuiaEncaminhamento sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    GuiaEncaminhamento guiaInvalida = new GuiaEncaminhamento();
    // Não definindo campos obrigatórios: id, nr e dt emissao
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(guiaInvalida); });
  }
  
}
