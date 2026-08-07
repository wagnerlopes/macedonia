package br.com.wagnersoft.macedonia.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsPmService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(RemoteApiRestController.class)
class RemoteApiRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GuiaEncaminhamentoService guiaSvc;

  @MockitoBean
  private OcsPmService ocsPmSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;
  
  @Nested
  @DisplayName("GET /api/opm/{ocsId}/{pmId}")
  class FindByIdTests {

    @Test
    @DisplayName("Deve retornar 200 OK e DTO do Procedimento quando encontrar o registro")
    void testFindByIdSucesso() throws Exception {
      // Arrange
      Ocs ocs = new Ocs();
      ocs.setId(1);

      ProcedimentoMedico pm = new ProcedimentoMedico();
      pm.setId(2);
      pm.setTuss("10101012");

      OcsPm ocsPm = new OcsPm();
      ocsPm.setId(10);
      ocsPm.setOcs(ocs);
      ocsPm.setPm(pm);
      ocsPm.setUnidadeMedida("US");
      ocsPm.setValorUnitario(new BigDecimal("150.50"));

      when(ocsPmSvc.findByOcsPm(1, 2)).thenReturn(Optional.of(ocsPm));

      // Act & Assert
      mockMvc.perform(get("/api/opm/1/2")
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(10))
      .andExpect(jsonPath("$.ocs_id").value(1))
      .andExpect(jsonPath("$.pm_id").value(2))
      .andExpect(jsonPath("$.tuss").value("10101012"))
      .andExpect(jsonPath("$.unidadeMedida").value("US"))
      .andExpect(jsonPath("$.valorUnitario").value(150.50));
    }

    @Test
    @DisplayName("Deve retornar 404 Not Found quando não encontrar o registro")
    void testFindByIdNaoEncontrado() throws Exception {
      // Arrange
      when(ocsPmSvc.findByOcsPm(anyInt(), anyInt())).thenReturn(Optional.empty());

      // Act & Assert
      mockMvc.perform(get("/api/opm/99/99")
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
    }
  }

  @Nested
  @DisplayName("GET /api/chart")
  class ComputeChartValuesTests {

    @Test
    @DisplayName("Deve retornar 200 OK com os 12 meses e valores computados corretamente")
    void testComputeChartValuesComDados() throws Exception {
      // Arrange: Simulando retorno de [mês (1 a 12), total] do banco de dados
      // Exemplo: Janeiro (mês 1) = 15 itens, Março (mês 3) = 42 itens
      List<Object[]> mockData = List.of(
          new Object[]{1, 15L},
          new Object[]{3, 42L}
          );

      when(guiaSvc.countByMonth(LocalDate.now().getYear())).thenReturn(mockData);

      // Act & Assert
      mockMvc.perform(get("/api/chart")
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.xValues.length()").value(12))
      .andExpect(jsonPath("$.xValues[0]").value("Jan"))
      .andExpect(jsonPath("$.xValues[11]").value("Dez"))
      .andExpect(jsonPath("$.yValues.length()").value(12))
      .andExpect(jsonPath("$.yValues[0]").value(15))  // Janeiro (15)
      .andExpect(jsonPath("$.yValues[1]").value(0))   // Fevereiro sem dados (0)
      .andExpect(jsonPath("$.yValues[2]").value(42));  // Março (42)
    }

    @Test
    @DisplayName("Deve retornar 200 OK com lista de yValues preenchida com zeros se não houver registros no ano")
    void testComputeChartValuesSemDados() throws Exception {
      // Arrange
      when(guiaSvc.countByMonth(anyInt())).thenReturn(Collections.emptyList());

      // Act & Assert
      mockMvc.perform(get("/api/chart")
          .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.xValues.length()").value(12))
      .andExpect(jsonPath("$.yValues.length()").value(12))
      .andExpect(jsonPath("$.yValues[0]").value(0))
      .andExpect(jsonPath("$.yValues[11]").value(0));
    }
  }

}
