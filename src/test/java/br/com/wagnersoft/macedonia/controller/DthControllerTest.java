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

import java.math.BigDecimal;
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

import br.com.wagnersoft.macedonia.model.Dth;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.service.DthService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(DthController.class)
public class DthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private DthService dthSvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Dth b1;

  private Dth b2;

  private Ocs ocs;

  @BeforeEach
  void setup() {
    ocs = new Ocs();
    ocs.setId(1);
    ocs.setCnpj("XXX1");
    ocs.setDescricao("Hospital Central");
    ocs.setEspecialidade("XYZ");    

    b1 = new Dth();
    b1.setId(1);
    b1.setCodigo("123");
    b1.setDescricao("Diaria 1");
    b1.setUnidadeMedida("USO");
    b1.setValorUnitario(BigDecimal.ONE);
    b1.setOcs(ocs);

    b2 = new Dth();
    b2.setId(2);
    b2.setCodigo("234");
    b2.setDescricao("Diaria 2");
    b2.setUnidadeMedida("USO");
    b2.setValorUnitario(BigDecimal.TWO);
    b2.setOcs(ocs);

    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(dthSvc.listAll()).thenReturn(List.of(b1, b2));
    when(ocsSvc.mapAll()).thenReturn(Map.of(1, "Hospital Central"));
  }

  @Test
  @DisplayName("GET /dth - Deve exibir a página de Dths com lista e formulário novo")
  void testShowSemId() throws Exception {
    mockMvc.perform(get("/dth"))
    .andExpect(status().isOk())
    .andExpect(view().name("dth"))
    .andExpect(model().attributeExists("dth"))
    .andExpect(model().attributeExists("allUnidadeMedida"))
    .andExpect(model().attributeExists("tipoUmMap"))
    .andExpect(model().attribute("allDth", List.of(b1, b2)));
  }

  @Test
  @DisplayName("GET /dth?cpf=... - Deve carregar Dth existente para edição")
  void testShowComId() throws Exception {
    // Arrange    
    when(dthSvc.findById(1)).thenReturn(Optional.of(b1));

    //Act
    mockMvc.perform(get("/dth").param("id", "1"))
    .andExpect(status().isOk())
    .andExpect(view().name("dth"))
    .andExpect(model().attribute("dth", b1));

    // Assert
    verify(dthSvc).findById(1);
  }

  @Test
  @DisplayName("POST /dth?delete - Deve remover Dth e redirecionar")
  void testDelete() throws Exception {

    mockMvc.perform(post("/dth")
        .param("delete", "")
        .param("id", "1"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/dth"));

    verify(dthSvc).remove(1);
  }

  @Test
  @DisplayName("POST /dth?save - Deve salvar Dth válido e redirecionar")
  void testSaveSucesso() throws Exception {
    // Arrange    
    when(ocsSvc.findById(1)).thenReturn(Optional.of(ocs));

    // Act
    mockMvc.perform(post("/dth")
        .param("save", "")
        .param("id", "1")
        .param("codigo", "123")
        .param("descricao", "diaria 1")
        .param("unidadeMedida", "uso")
        .param("valorUnitario", "100.19")
        .param("ocs.id", "1")
        )
    //.andDo(MockMvcResultHandlers.print()) --> somente para debug da request
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/dth"));

    // Assert
    verify(dthSvc).add(any(Dth.class));
  }

  @Test
  @DisplayName("POST /dth?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Arrange
    when(ocsSvc.findById(1)).thenReturn(Optional.of(ocs));

    // Act
    mockMvc.perform(post("/dth")
        .param("save", "")
        .param("codigo", "")) // valor inválido para retornar erro
    .andExpect(status().isOk())
    .andExpect(view().name("dth"))
    .andExpect(model().hasErrors());

    // Assert
    verify(dthSvc, never()).add(any(Dth.class));
  }

}
