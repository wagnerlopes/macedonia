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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(BeneficiarioController.class)
public class BeneficiarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BeneficiarioService benSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private Beneficiario b1;
  private Beneficiario b2;

  @BeforeEach
  void setup() {
    b1 = new Beneficiario();
    b1.setCpf("11111111111");
    b1.setNome("Ana");

    b2 = new Beneficiario();
    b2.setCpf("22222222222");
    b2.setNome("Carlos");

    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(benSvc.listAll()).thenReturn(List.of(b1, b2));
  }

  @Test
  @DisplayName("GET /beneficiarios - Deve exibir a página de beneficiários com lista e formulário novo")
  void testShowSemCpf() throws Exception {
    mockMvc.perform(get("/beneficiarios"))
    .andExpect(status().isOk())
    .andExpect(view().name("beneficiarios"))
    .andExpect(model().attribute("menu", "ben"))
    .andExpect(model().attributeExists("beneficiario"))
    .andExpect(model().attribute("allBeneficiarios", List.of(b1, b2)));
  }

  @Test
  @DisplayName("GET /beneficiarios?cpf=... - Deve carregar beneficiário existente para edição")
  void testShowComCpf() throws Exception {
    when(benSvc.findByCpf("11111111111")).thenReturn(Optional.of(b1));

    mockMvc.perform(get("/beneficiarios").param("cpf", "11111111111"))
    .andExpect(status().isOk())
    .andExpect(view().name("beneficiarios"))
    .andExpect(model().attribute("beneficiario", b1));

    verify(benSvc).findByCpf("11111111111");
  }

  @Test
  @DisplayName("POST /beneficiarios?delete - Deve remover beneficiário e redirecionar")
  void testDelete() throws Exception {
    mockMvc.perform(post("/beneficiarios")
        .param("delete", "")
        .param("cpf", "11111111111"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/beneficiarios"));

    verify(benSvc).remove("11111111111");
  }

  @Test
  @DisplayName("POST /beneficiarios?save - Deve salvar beneficiário válido e redirecionar")
  void testSaveSucesso() throws Exception {
    mockMvc.perform(post("/beneficiarios")
        .param("save", "")
        .param("cpf", "11111111111")
        .param("nome", "Ana")
        .param("nascimentoData", "1995-03-15")
        )
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/beneficiarios"));

    verify(benSvc).add(any(Beneficiario.class));
  }

  @Test
  @DisplayName("POST /beneficiarios?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Supondo que 'cpf' ou 'nome' sejam obrigatórios pela anotação @Valid no model Beneficiario
    mockMvc.perform(post("/beneficiarios")
        .param("save", "")
        .param("cpf", "")) // Valor inválido
    .andExpect(status().isOk())
    .andExpect(view().name("beneficiarios"))
    .andExpect(model().hasErrors());

    verify(benSvc, never()).add(any(Beneficiario.class));
  }

}
