package br.com.wagnersoft.macedonia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.model.Ocs;

@DataJpaTest
class DthRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("Deve persistir e recuperar DTH")
  void devePersistirDth() {
    // Arrange
    Ocs ocs = new Ocs();
    ocs.setCnpj("123456789010001");
    ocs.setDescricao("XXX");
    ocs.setEspecialidade("ZZZ");
    Ocs persist = entityManager.persist(ocs);

    BigDecimal valor = BigDecimal.valueOf(100.98);

    Dth Dth = new Dth();
    Dth.setCodigo("1");
    Dth.setDescricao("Teste 1");
    Dth.setUnidadeMedida("Diaria");
    Dth.setValorUnitario(valor);
    Dth.setOcs(persist);

    // Act: salva e limpa o contexto para forçar a leitura do BD
    Dth DthSalvo = entityManager.persistAndFlush(Dth);
    entityManager.clear();
    
    assertThat(DthSalvo.getId()).isNotNull();
    Dth DthBuscado = entityManager.find(Dth.class, DthSalvo.getId());

    // Assert
    assertThat(DthBuscado.getCodigo()).isEqualTo("1");
    assertThat(DthBuscado.getDescricao()).isEqualTo("Teste 1");
    assertThat(DthBuscado.getUnidadeMedida()).isEqualTo("Diaria");
    assertThat(DthBuscado.getValorUnitario()).isEqualTo(valor);
    assertThat(DthBuscado.getOcs()).isEqualTo(ocs);
  }

  @Test
  @DisplayName("Não deve persistir Dth sem campos obrigatórios")
  void naoDevePersistirSemCamposObrigatorios() {
    Dth DthInvalido = new Dth();
    // Não definindo campos obrigatórios: codigo, descricao e ocs
    assertThrows(Exception.class, () -> { entityManager.persistAndFlush(DthInvalido); });
  }

}
