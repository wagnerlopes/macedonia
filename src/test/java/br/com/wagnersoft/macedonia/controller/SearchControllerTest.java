package br.com.wagnersoft.macedonia.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wagnersoft.macedonia.model.Beneficiario;
import br.com.wagnersoft.macedonia.model.Cbo;
import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.service.BeneficiarioService;
import br.com.wagnersoft.macedonia.service.CboService;
import br.com.wagnersoft.macedonia.service.GuiaEncaminhamentoService;
import br.com.wagnersoft.macedonia.service.OcsService;
import br.com.wagnersoft.macedonia.service.ProcedimentoMedicoService;
import br.com.wagnersoft.macedonia.service.ProfissionalService;
import br.com.wagnersoft.macedonia.viewmodel.GuiaEncaminhamentoViewModelBuilder;

@WebMvcTest(SearchController.class)
class SearchControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BeneficiarioService benSvc;

  @MockitoBean
  private CboService cboSvc;

  @MockitoBean
  private OcsService ocsSvc;

  @MockitoBean
  private ProcedimentoMedicoService pmSvc;

  @MockitoBean
  private ProfissionalService profSvc;

  @MockitoBean
  private GuiaEncaminhamentoService guiaSvc;

  @MockitoBean
  private GuiaEncaminhamentoViewModelBuilder guiaViewModelBuilder;

  @Nested
  @DisplayName("Busca de Beneficiários")
  class BeneficiarioSearchTests {

    @Test
    @DisplayName("Deve buscar beneficiários por nome e retornar view 'beneficiarios'")
    void testSearchBeneficiario() throws Exception {
      var b1 = new Beneficiario();
      b1.setNome("Maria Silva");

      when(benSvc.findByNome("Maria")).thenReturn(List.of(b1));

      mockMvc.perform(post("/search")
          .param("tipo", "beneficiario")
          .param("nome", "Maria"))
      .andExpect(status().isOk())
      .andExpect(view().name("beneficiarios"))
      .andExpect(model().attribute("allBeneficiarios", List.of(b1)))
      .andExpect(model().attributeExists("beneficiario"));

      verify(benSvc).findByNome("Maria");
    }
  }

  @Nested
  @DisplayName("Busca de Estabelecimentos (OCS)")
  class OcsSearchTests {

    @Test
    @DisplayName("Deve buscar OCS por descrição e retornar view 'ocs' com mapas auxiliares")
    void testSearchEstabelecimento() throws Exception {
      var ocs = new Ocs();
      ocs.setDescricao("Hospital Central");
      ocs.setEspecialidade("01");

      when(ocsSvc.findByDescricao("Central")).thenReturn(List.of(ocs));
      when(pmSvc.mapAll()).thenReturn(Map.of(1, "Procedimento A"));

      mockMvc.perform(post("/search")
          .param("tipo", "estabelecimento")
          .param("nome", "Central"))
      .andExpect(status().isOk())
      .andExpect(view().name("ocs"))
      .andExpect(model().attribute("allOcs", List.of(ocs)))
      .andExpect(model().attribute("allProcedimentos", Map.of(1, "Procedimento A")))
      .andExpect(model().attributeExists("tipoOcsMap"))
      .andExpect(model().attributeExists("ocs"));

      verify(ocsSvc).findByDescricao("Central");
    }
  }

  @Nested
  @DisplayName("Busca de Profissionais")
  class ProfissionalSearchTests {

    @Test
    @DisplayName("Deve buscar profissionais por nome e retornar view 'profissionais'")
    void testSearchProfissional() throws Exception {
      var cbo = new Cbo();
      cbo.setCodigo("1234");
      cbo.setDescricao("Medico");
      
      var prof = new Profissional();
      prof.setNome("Dr. Carlos");
      prof.setCbo(cbo);
      
      when(profSvc.findByNome("Carlos")).thenReturn(List.of(prof));
      when(cboSvc.mapAll()).thenReturn(Map.of("1234", "Médico"));

      mockMvc.perform(post("/search")
          .param("tipo", "profissional")
          .param("nome", "Carlos"))
      .andExpect(status().isOk())
      .andExpect(view().name("profissionais"))
      .andExpect(model().attribute("allProfissionais", List.of(prof)))
      .andExpect(model().attribute("allCbo", Map.of("1234", "Médico")))
      .andExpect(model().attributeExists("allConselho"))
      .andExpect(model().attributeExists("profissional"));

      verify(profSvc).findByNome("Carlos");
    }
  }

  @Nested
  @DisplayName("Busca de Guias de Encaminhamento")
  class GuiaSearchTests {

    @Test
    @DisplayName("Deve buscar guia por número e retornar view 'guias'")
    void testSearchGuia() throws Exception {
      var b1 = new Beneficiario();
      b1.setCpf("11111111111");
      b1.setNome("Maria Silva");

      var ocs = new Ocs();
      ocs.setId(1);
      ocs.setCnpj("XXXX");
      ocs.setDescricao("HOSPITAL X");
      
      var guia = new GuiaEncaminhamento();
      guia.setGuiaNr(1001);
      guia.setBeneficiario(b1);
      guia.setOcs(ocs);
      
      when(guiaSvc.findByGuiaNr(1001)).thenReturn(List.of(guia));

      mockMvc.perform(post("/search")
          .param("tipo", "guia")
          .param("nome", "1001"))
      .andExpect(status().isOk())
      .andExpect(view().name("guias"))
      .andExpect(model().attribute("allGuias", List.of(guia)))
      .andExpect(model().attributeExists("guiaEncaminhamento"));

      verify(guiaViewModelBuilder).populateGuias(org.mockito.ArgumentMatchers.any());
      verify(guiaSvc).findByGuiaNr(1001);
    }
  }

  @Nested
  @DisplayName("Tratamento de Exceções")
  class ExceptionTests {

    @Test
    @DisplayName("Deve redirecionar para a view de erro quando o tipo de pesquisa for inválido")
    void testSearchTipoInvalido() throws Exception {
      mockMvc.perform(post("/search")
              .param("tipo", "inexistente")
              .param("nome", "teste"))
          .andExpect(status().isOk())
          .andExpect(view().name("configuracoes"))
          .andExpect(model().attribute("emsg", "Pesquisa inválida: inexistente"));
    }
  }
  
}
