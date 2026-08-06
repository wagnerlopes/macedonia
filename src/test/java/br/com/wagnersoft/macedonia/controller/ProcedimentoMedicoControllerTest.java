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

import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(ProcedimentoMedicoController.class)
public class ProcedimentoMedicoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProcedimentoMedicoService pmSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private ProcedimentoMedico pm;

  @BeforeEach
  void setup() {
    pm = new ProcedimentoMedico();
    pm.setId(1);
    pm.setDescricao("YYY");
  }

  @Test
  @DisplayName("GET /procedimentos - Deve exibir a página de procedimentos com lista")
  void testProcedimentos() throws Exception {
    
    when(pmSvc.listAll()).thenReturn(List.of(pm));

    mockMvc.perform(get("/procedimentos"))
    .andExpect(status().isOk())
    .andExpect(view().name("procedimentos"))
    .andExpect(model().attributeExists("allProcedimentos"));
  }

}
