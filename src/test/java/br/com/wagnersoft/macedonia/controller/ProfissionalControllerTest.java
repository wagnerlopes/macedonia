package br.com.wagnersoft.macedonia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(ProfissionalController.class)
public class ProfissionalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ProfissionalService profSvc;

  @MockitoBean
  private CboService cboSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Profissional b1;

  private Profissional b2;

  private Cbo cbo;
  
  @BeforeEach
  void setup() {
    cbo = new Cbo();
    cbo.setCodigo("123");
    cbo.setDescricao("XYZ");
    
    b1 = new Profissional();
    b1.setCpf("11111111111");
    b1.setNome("Ana");
    b1.setCbo(cbo);

    b2 = new Profissional();
    b2.setCpf("22222222222");
    b2.setNome("Carlos");
    b2.setCbo(cbo);

    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(profSvc.listAll()).thenReturn(List.of(b1, b2));
  }

  @Test
  @DisplayName("GET /profissionais - Deve exibir a página de profissionais com lista e formulário novo")
  void testShowSemCpf() throws Exception {
    mockMvc.perform(get("/profissionais"))
    .andExpect(status().isOk())
    .andExpect(view().name("profissionais"))
    .andExpect(model().attribute("menu", "prof"))
    .andExpect(model().attributeExists("profissional"))
    .andExpect(model().attribute("allProfissionais", List.of(b1, b2)));
  }

  @Test
  @DisplayName("GET /profissionais?cpf=... - Deve carregar profissional existente para edição")
  void testShowComCpf() throws Exception {
    when(profSvc.findByCpf("11111111111")).thenReturn(Optional.of(b1));

    mockMvc.perform(get("/profissionais").param("cpf", "11111111111"))
    .andExpect(status().isOk())
    .andExpect(view().name("profissionais"))
    .andExpect(model().attribute("profissional", b1));

    verify(profSvc).findByCpf("11111111111");
  }

  @Test
  @DisplayName("POST /profissionais?delete - Deve remover profissional e redirecionar")
  void testDelete() throws Exception {
    mockMvc.perform(post("/profissionais")
        .param("delete", "")
        .param("cpf", "11111111111"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/profissionais"));

    verify(profSvc).remove("11111111111");
  }

  @Test
  @DisplayName("POST /profissionais?save - Deve salvar profissional válido e redirecionar")
  void testSaveSucesso() throws Exception {
    // Arrange
    when(cboSvc.findById(anyString())).thenReturn(Optional.of(cbo));
    
    // Act
    mockMvc.perform(post("/profissionais")
        .param("save", "")
        .param("cpf", "11111111111")
        .param("nome", "Ana")
        .param("cbo.codigo", "222")
        .param("registroProfissional.numero", "1111")
        .param("registroProfissional.conselho", "CRM")
        .param("registroProfissional.uf", "SP")
        )
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/profissionais"));

    // Assert
    verify(profSvc).add(any(Profissional.class));
  }

  @Test
  @DisplayName("POST /profissionais?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Arrange
    when(cboSvc.findById(anyString())).thenReturn(Optional.of(cbo));
    
    // Supondo que 'cpf' ou 'nome' sejam obrigatórios pela anotação @Valid no model Beneficiario
    mockMvc.perform(post("/profissionais")
        .param("save", "")
        .param("cpf", "") // Valor inválido
        .param("cbo.codigo", "222")
        .param("registroProfissional.numero", "1111")
        .param("registroProfissional.conselho", "CRM")
        .param("registroProfissional.uf", "SP"))
    .andExpect(status().isOk())
    .andExpect(view().name("profissionais"))
    .andExpect(model().hasErrors());

    verify(profSvc, never()).add(any(Profissional.class));
  }

}
