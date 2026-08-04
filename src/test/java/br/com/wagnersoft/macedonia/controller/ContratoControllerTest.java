package br.com.wagnersoft.macedonia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Contrato;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.ContratoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ContratoService cttSvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Contrato b1;
  private Contrato b2;

  private Ocs o1;
  private Ocs o2;

  @BeforeEach
  void setup() {
    o1 = new Ocs();
    o1.setId(1);
    o1.setCnpj("XXX1");
    o1.setDescricao("Hospital Central");
    o1.setEspecialidade("XYZ");    

    o2 = new Ocs();
    o2.setId(2);
    o2.setCnpj("XXX2");
    o2.setDescricao("O2");
    o2.setEspecialidade("XYZ");    

    b1 = new Contrato();
    b1.setId(1);
    b1.setOcs(o1);

    b2 = new Contrato();
    b2.setId(2);
    b2.setOcs(o2);

    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(cttSvc.listAll()).thenReturn(List.of(b1, b2));
    when(ocsSvc.mapAll()).thenReturn(Map.of(1, "Hospital Central"));
  }

  @Test
  @DisplayName("GET /contratos - Deve exibir a página de contratos com lista e formulário novo")
  void testShowSemId() throws Exception {
    mockMvc.perform(get("/contratos"))
    .andExpect(status().isOk())
    .andExpect(view().name("contratos"))
    .andExpect(model().attribute("menu", "Contrato"))
    .andExpect(model().attributeExists("contrato"))
    .andExpect(model().attribute("allContratos", List.of(b1, b2)));
  }

  @Test
  @DisplayName("GET /contratos?cpf=... - Deve carregar contrato existente para edição")
  void testShowComId() throws Exception {
    // Arrange    
    when(cttSvc.findById(1)).thenReturn(Optional.of(b1));

    //Act
    mockMvc.perform(get("/contratos").param("id", "1"))
    .andExpect(status().isOk())
    .andExpect(view().name("contratos"))
    .andExpect(model().attribute("contrato", b1));

    // Assert
    verify(cttSvc).findById(1);
  }

  @Test
  @DisplayName("POST /contratos?delete - Deve remover contrato e redirecionar")
  void testDelete() throws Exception {
    
    mockMvc.perform(post("/contratos")
           .param("delete", "")
           .param("id", "1"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/contratos"));

    verify(cttSvc).remove(1);
  }

  @Test
  @DisplayName("POST /contratos?save - Deve salvar contrato válido e redirecionar")
  void testSaveSucesso() throws Exception {
    // Arrange    
    when(ocsSvc.findById(1)).thenReturn(Optional.of(o1));

    // Act
    mockMvc.perform(post("/contratos")
        .param("save", "")
        .param("id", "1")
        .param("ocs.id", "1")
        .param("inicioData", "2026-01-01")
        .param("terminoData", "2026-12-31")
        .param("chQtd", "10")
        )
    //.andDo(MockMvcResultHandlers.print()) --> somente para debug da request
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/contratos"));

    // Assert
    verify(cttSvc).add(any(Contrato.class));
  }

  @Test
  @DisplayName("POST /contratos?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Arrange
    when(ocsSvc.findById(1)).thenReturn(Optional.of(o1));

    // Act
    mockMvc.perform(post("/contratos")
        .param("save", "")
        .param("inicioData", "")) // valor inválido para retornar erro
    .andExpect(status().isOk())
    .andExpect(view().name("contratos"))
    .andExpect(model().hasErrors());

    // Assert
    verify(cttSvc, never()).add(any(Contrato.class));
  }

}
