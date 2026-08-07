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

import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.OcsPm;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.service.OcsPmService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(OcsPmController.class)
public class OcsPmControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OcsPmService opmSvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private ProcedimentoMedicoService pmSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaBuilder;

  private OcsPm b1;

  private Ocs o1;

  @BeforeEach
  void setup() {
    o1 = new Ocs();
    o1.setId(1);
    o1.setCnpj("XXX1");
    o1.setDescricao("Hospital Central");
    o1.setEspecialidade("XYZ");    

    ProcedimentoMedico pm = new ProcedimentoMedico();
    pm.setId(1);
    pm.setDescricao("XYZ");
    pm.setTuss("XXX");
    
    b1 = new OcsPm();
    b1.setId(1);
    b1.setOcs(o1);
    b1.setPm(pm);
    b1.setUnidadeMedida("USO");
    
    // Prepara o mock para responder sempre que a controller chamar listAll() (via @ModelAttribute)
    when(opmSvc.listAll()).thenReturn(List.of(b1));
    when(ocsSvc.mapAll()).thenReturn(Map.of(1, "Hospital Central"));
    when(pmSvc.mapAll()).thenReturn(Map.of(1, "XYZ"));
  }

  @Test
  @DisplayName("GET /ocspm?id=... - Deve carregar ocspm existente para edição")
  void testShowComId() throws Exception {
    // Arrange    
    when(opmSvc.findById(1)).thenReturn(Optional.of(b1));

    //Act
    mockMvc.perform(get("/ocspm")
        .param("ocsid", "1")
        .param("id", "1")
        )
    .andExpect(status().isOk())
    .andExpect(view().name("ocspm"))
    .andExpect(model().attribute("ocspm", b1));

    // Assert
    verify(opmSvc).findById(1);
  }

  @Test
  @DisplayName("POST /ocspm?delete - Deve remover contrato e redirecionar")
  void testDelete() throws Exception {

    mockMvc.perform(post("/ocspm")
        .param("delete", "")
        .param("ocsid", "1")
        .param("id", "1")
        )
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/ocspm?ocsid=1"));

    verify(opmSvc).remove(1);
  }

  @Test
  @DisplayName("POST /ocspm?save - Deve salvar ocspm válido e redirecionar")
  void testSaveSucesso() throws Exception {
    // Arrange    
    when(ocsSvc.findById(1)).thenReturn(Optional.of(o1));

    // Act
    mockMvc.perform(post("/ocspm")
        .param("save", "")
        .param("id", "1")
        .param("ocs.id", "1")
        .param("pm.id", "1")
        )
    //.andDo(MockMvcResultHandlers.print()) //--> somente para debug da request
    .andExpect(status().is3xxRedirection())
    .andExpect(redirectedUrl("/ocspm?ocsid=1"));

    // Assert
    verify(opmSvc).add(any(OcsPm.class));
  }

  @Test
  @DisplayName("POST /ocspm?save - Deve retornar para a view em caso de erro de validação")
  void testSaveComErrosDeValidacao() throws Exception {
    // Arrange
    when(ocsSvc.findById(1)).thenReturn(Optional.of(o1));

    // Act
    mockMvc.perform(post("/ocspm")
        .param("save", "")
        .param("id", "")) // valor inválido para retornar erro
    .andExpect(status().isOk())
    .andExpect(view().name("ocspm"))
    .andExpect(model().hasErrors());

    // Assert
    verify(opmSvc, never()).add(any(OcsPm.class));
  }

}
