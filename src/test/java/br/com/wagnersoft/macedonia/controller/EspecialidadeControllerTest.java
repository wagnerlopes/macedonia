package br.com.wagnersoft.macedonia.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(EspecialidadeController.class)
public class EspecialidadeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CboService cboSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Cbo cbo;

  @BeforeEach
  void setup() {
    cbo = new Cbo();
    cbo.setCodigo("XXX");
    cbo.setDescricao("YYY");
  }

  @Test
  @DisplayName("GET /especialidades - Deve exibir a página de Especialidades com lista")
  void testEspecialidades() throws Exception {
    
    when(cboSvc.listAll()).thenReturn(List.of(cbo));

    mockMvc.perform(get("/especialidades"))
    .andExpect(status().isOk())
    .andExpect(view().name("especialidades"))
    .andExpect(model().attributeExists("lista"));
  }

}
