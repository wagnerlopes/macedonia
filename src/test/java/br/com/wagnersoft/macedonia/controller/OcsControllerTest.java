package br.com.wagnersoft.macedonia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(OcsController.class)
public class OcsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private ProcedimentoMedicoService pmSvc;
  
  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Ocs ocs;

  private ProcedimentoMedico pm;

  @BeforeEach
  void setup() {
    ocs = new Ocs();
    ocs.setId(1);
    ocs.setCnpj("CNPJ");
    ocs.setDescricao("Hospital Central");
    ocs.setEspecialidade("XYZ");    

    pm= new ProcedimentoMedico();
    pm.setId(1);
    pm.setDescricao("XYZ");
    
    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
    when(ocsSvc.listAll()).thenReturn(List.of(ocs));
    when(pmSvc.mapAll()).thenReturn(Map.of(1, "XYZ"));
  }

  @Test
  @DisplayName("GET /ocs - Deve exibir a página de ocs com lista e formulário novo")
  void testShowSemId() throws Exception {
    mockMvc.perform(get("/ocs"))
    .andExpect(status().isOk())
    .andExpect(view().name("ocs"))
    .andExpect(model().attribute("menu", "ocs"))
    .andExpect(model().attribute("allOcs", List.of(ocs)))
    .andExpect(model().attributeExists("procedimentos"));
  }

  @Test
  @DisplayName("GET /ocs?id=... - Deve carregar ocs existente para edição")
  void testShowComId() throws Exception {
    // Arrange    
    when(ocsSvc.findById(1)).thenReturn(Optional.of(ocs));

    //Act
    mockMvc.perform(get("/ocs").param("id", "1"))
    .andExpect(status().isOk())
    .andExpect(view().name("ocs"))
    .andExpect(model().attribute("ocs", ocs));

    // Assert
    verify(ocsSvc).findById(1);
  }

  @Test
  @DisplayName("POST /ocs?delete - Deve remover ocs e redirecionar")
  void testDelete() throws Exception {
    mockMvc.perform(post("/ocs")
        .param("delete", "")
        .param("id", "1"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/ocs"));

    verify(ocsSvc).remove(1);
  }

  @Test
  @DisplayName("POST /ocs?save - Deve salvar ocs válido e redirecionar")
  void testSaveSucesso() throws Exception {
    mockMvc.perform(post("/ocs")
        .param("save", "")
        .param("id", "1")
        .param("descricao", "OCS 1")
        .param("especialidade", "XYZ")
        .param("cnpj", "CNPJ")
        )
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/ocs"));

    verify(ocsSvc).add(any(Ocs.class));
  }
  
  @Test
  @DisplayName("POST /ocs?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Act
    mockMvc.perform(post("/ocs")
        .param("save", "")
        .param("id", "1")
        .param("descricao","OCS 1")
        .param("especialidade","XXX")
        .param("cnpj", "")) // valor inválido para retornar erro
    .andExpect(status().isOk())
    .andExpect(view().name("ocs"))
    .andExpect(model().hasErrors());

    // Assert
    verify(ocsSvc, never()).add(any(Ocs.class));
  }

  @Test
  @DisplayName("POST /ocs?addRow=... - Deve adicionar procedimento no ocs existente")
  void testAddRow() throws Exception {
    //Act
    mockMvc.perform(post("/ocs")
        .param("addRow", "")
        .flashAttr("ocs", ocs))
    .andExpect(status().isOk())
    .andExpect(view().name("ocs"))
    .andExpect(model().attributeExists("procedimentos"))
    .andExpect(model().attribute("ocs", Matchers.hasProperty("procedimentos", Matchers.hasSize(1))));
  }

  @Test
  @DisplayName("POST /ocs?removeRow=... - Deve remover procedimento do ocs existente")
  void testRemoveRow() throws Exception {
    // Arrange
    ocs.getProcedimentos().add(new OcsPm()); // adiciona um procedimento para ser removido

    //Act
    mockMvc.perform(post("/ocs")
        .param("removeRow", "0")
        .flashAttr("ocs", ocs))
    .andExpect(status().isOk())
    .andExpect(view().name("ocs"))
    .andExpect(model().attributeExists("procedimentos"))
    .andExpect(model().attribute("ocs", Matchers.hasProperty("procedimentos", Matchers.empty())));
  }

}
