package br.com.wagnersoft.macedonia.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.service.TissService;
import br.com.wagnersoft.macedonia.tiss.GuiaFaturamento;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(TissController.class)
class TissControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private TissService tissSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaViewModelBuilder;

  @Test
  @DisplayName("Deve retornar 200 OK e DTO com GuiaFaturamento vazia")
  void testGetAllSucesso() throws Exception {
    mockMvc.perform(get("/api/tiss").accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.guiaFaturamento").exists());
  }

  @Test
  @DisplayName("Deve retornar 200 OK com o DTO contendo a GuiaFaturamento encontrada")
  void testGetByIdSucesso() throws Exception {
    // Arrange
    Integer idMock = 1;
    GuiaFaturamento guiaMock = GuiaFaturamento.empty();
    when(tissSvc.findById(idMock)).thenReturn(guiaMock);

    // Act & Assert
    mockMvc.perform(get("/api/tiss/{id}", idMock)
        .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.guiaFaturamento").exists());

    verify(tissSvc).findById(idMock);
  }

}
