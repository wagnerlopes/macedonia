package br.com.wagnersoft.macedonia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(GuiaEncaminhamentoController.class)
public class GuiaEncaminhamentoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GuiaEncaminhamentoService guiaSvc;

  @MockitoBean
  private BeneficiarioService benSvc;

  @MockitoBean
  private ProfissionalService profSvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private GuiaEncaminhamento b1;
  private GuiaEncaminhamento b2;

  private Ocs ocs;

  private Beneficiario ben;

  private Profissional prof;

  @BeforeEach
  void setup() {
    ben = new Beneficiario();
    ben.setCpf("11111111111");
    ben.setNome("Ben");
    ben.setNascimentoData(LocalDate.of(2000, 1, 1));

    prof = new Profissional();
    prof.setCpf("22222222222");
    prof.setNome("Prof");

    ocs = new Ocs();
    ocs.setId(1);
    ocs.setCnpj("XXX1");
    ocs.setDescricao("Hospital Central");
    ocs.setEspecialidade("XYZ");    

    b1 = new GuiaEncaminhamento();
    b1.setId(1);
    b1.setOcs(ocs);
    b1.setEmissaoData(LocalDate.of(2026, 7, 1));
    b1.setBeneficiario(new Beneficiario());
    b1.setResponsavel(new Profissional());
    b1.setSolicitante(new Profissional());
    b1.setGuiaNr(1);

    b2 = new GuiaEncaminhamento();
    b2.setId(2);
    b2.setOcs(ocs);
    b2.setEmissaoData(LocalDate.of(2026, 7, 2));
    b2.setBeneficiario(new Beneficiario());
    b2.setResponsavel(new Profissional());
    b2.setSolicitante(new Profissional());
    b2.setGuiaNr(2);

    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(guiaSvc.listAll()).thenReturn(List.of(b1, b2));
    when(ocsSvc.mapAll()).thenReturn(Map.of(1, "Hospital Central"));
  }

  @Test
  @DisplayName("GET /guias - Deve exibir a página de guias com lista e formulário novo")
  void testShowSemId() throws Exception {
    mockMvc.perform(get("/guias"))
    .andExpect(status().isOk())
    .andExpect(view().name("guias"))
    .andExpect(model().attribute("menu", "guias"))
    .andExpect(model().attribute("allGuias", List.of(b1, b2)))
    .andExpect(model().attributeExists("guiaEncaminhamento"))
    .andExpect(model().attributeExists("opm"));
  }

  @Test
  @DisplayName("GET /guias?id=... - Deve carregar guia existente para edição")
  void testShowComId() throws Exception {
    // Arrange    
    when(guiaSvc.findById(1)).thenReturn(Optional.of(b1));

    //Act
    mockMvc.perform(get("/guias").param("id", "1"))
    .andExpect(status().isOk())
    .andExpect(view().name("guias"))
    .andExpect(model().attribute("guiaEncaminhamento", b1));

    // Assert
    verify(guiaSvc).findById(1);
  }

  @Test
  @DisplayName("POST /guias?delete - Deve remover guia e redirecionar")
  void testDelete() throws Exception {

    mockMvc.perform(post("/guias")
        .param("delete", "")
        .param("id", "1"))
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/guias"));

    verify(guiaSvc).remove(1);
  }
  /*
  @Test
  @DisplayName("POST /guias?save - Deve salvar guia válida e redirecionar")
  void testSaveSucesso() throws Exception {
    // Arrange    
    when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
    when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
    when(profSvc.findByCpf(anyString())).thenReturn(Optional.of(prof));

    // Act
    mockMvc.perform(post("/guias")
        .param("save", "")
        .param("ocs.id", "1")
        .param("emissaoData","2026-07-01")
        .param("beneficiario.cpf","1")
        .param("responsavel.cpf","1")
        .param("solicitante.cpf","1")
        .param("guiaNr","1"))
    //.andDo(MockMvcResultHandlers.print()) //--> somente para debug da request
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/guias"));

    // Assert
    verify(guiaSvc).add(any(GuiaEncaminhamento.class));
  }
   */
  @Test
  @DisplayName("POST /guias?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Arrange
    when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
    when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
    when(profSvc.findByCpf(anyString())).thenReturn(Optional.of(prof));

    // Act
    mockMvc.perform(post("/guias")
        .param("save", "")
        .param("ocs.id", "1")
        .param("beneficiario.cpf","")
        .param("responsavel.cpf","")
        .param("solicitante.cpf","")
        .param("emissaoData", "")) // valor inválido para retornar erro
    .andExpect(status().isOk())
    .andExpect(view().name("guias"))
    .andExpect(model().hasErrors());

    // Assert
    verify(guiaSvc, never()).add(any(GuiaEncaminhamento.class));
  }

  @Test
  @DisplayName("POST /guias?addRow=... - Deve adicionar procedimento na guia existente")
  void testAddRow() throws Exception {
    // Arrange
    when(ocsSvc.findById(1)).thenReturn(Optional.of(ocs));

    //Act
    mockMvc.perform(post("/guias")
        .param("addRow", "")
        .param("ocs.id", "1")
        .flashAttr("guiaEncaminhamento", b1))
    .andExpect(status().isOk())
    .andExpect(view().name("guias"))
    .andExpect(model().attributeExists("opm"))
    .andExpect(model().attribute("guia", Matchers.hasProperty("procedimentos", Matchers.hasSize(1))));
  }

  @Test
  @DisplayName("POST /guias?removeRow=... - Deve remover procedimento da guia existente")
  void testRemoveRow() throws Exception {
    // Arrange
    b1.getProcedimentos().add(new GuiaPm()); // adiciona um procedimento para ser removido

    when(ocsSvc.findById(1)).thenReturn(Optional.of(ocs));

    //Act
    mockMvc.perform(post("/guias")
        .param("removeRow", "0")
        .param("ocs.id", "1")
        .flashAttr("guiaEncaminhamento", b1))
    .andExpect(status().isOk())
    .andExpect(view().name("guias"))
    .andExpect(model().attributeExists("opm"))
    .andExpect(model().attribute("guia", Matchers.hasProperty("procedimentos", Matchers.empty())));
  }

  @ParameterizedTest
  @MethodSource("provideCenariosSave")
  @DisplayName("POST /guias?save - Deve salvar ou falhar conforme cenário")
  void testSave(String cenario) throws Exception {
    // Arrange
    switch (cenario) {
    case "beneficiario":
      when(benSvc.findByCpf(anyString())).thenReturn(Optional.empty());
      when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
      when(profSvc.findByCpf(anyString())).thenReturn(Optional.of(prof));
      break;
    case "ocs":
      when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
      when(ocsSvc.findById(anyInt())).thenReturn(Optional.empty());
      when(profSvc.findByCpf(anyString())).thenReturn(Optional.of(prof));
      break;
    case "solicitante":
      when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
      when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
      when(profSvc.findByCpf("1")).thenReturn(Optional.empty()); // solicitante falha
      when(profSvc.findByCpf("2")).thenReturn(Optional.of(prof)); // responsavel ok
      break;
    case "responsavel":
      when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
      when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
      when(profSvc.findByCpf("1")).thenReturn(Optional.of(prof)); // solicitante ok
      when(profSvc.findByCpf("2")).thenReturn(Optional.empty()); // responsavel falha
      break;
    case "sucesso":
      when(benSvc.findByCpf(anyString())).thenReturn(Optional.of(ben));
      when(ocsSvc.findById(anyInt())).thenReturn(Optional.of(ocs));
      when(profSvc.findByCpf(anyString())).thenReturn(Optional.of(prof));
      break;
    }

    // Act
    ResultActions result = mockMvc.perform(post("/guias")
        .param("save", "")
        .param("ocs.id", "1")
        .param("emissaoData","2026-07-01")
        .param("beneficiario.cpf","1")
        .param("responsavel.cpf","2")
        .param("solicitante.cpf","1")
        .param("guiaNr","1"));

    // Assert
    if ("sucesso".equals(cenario)) {
      result.andExpect(status().is3xxRedirection())
      .andExpect(redirectedUrl("/guias"));
      verify(guiaSvc).add(any(GuiaEncaminhamento.class));
    } else {
      result.andExpect(status().isOk())
      .andExpect(view().name("guias"))
      .andExpect(model().hasErrors());
    }
  }

  static Stream<String> provideCenariosSave() {
    return Stream.of("beneficiario", "ocs", "solicitante", "responsavel", "sucesso");
  }

}
