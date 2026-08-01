package br.com.wagnersoft.macedonia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.model.Ocs;
import br.com.wagnersoft.macedonia.model.ProcedimentoMedico;
import br.com.wagnersoft.macedonia.model.Profissional;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.tiss.GuiaFaturamento;
import br.com.wagnersoft.macedonia.tiss.Procedimento;

@ExtendWith(MockitoExtension.class)
class TissServiceTest {

  @Mock
  private GuiaEncaminhamentoRepository repository;

  @InjectMocks
  private TissService tissService;

  @BeforeEach
  void setUp() {
    // Injeta os valores anotados com @Value no Spring que não são injetados automaticamente pelo Mockito
    ReflectionTestUtils.setField(tissService, "operadora", "Operadora Teste");
    ReflectionTestUtils.setField(tissService, "registroAns", "123456");
  }

  @Test
  @DisplayName("Deve retornar GuiaFaturamento corretamente preenchida quando encontrar a guia")
  void findById_Sucesso() {
    // --- 1. CENÁRIO (ARRANGE) ---
    Integer guiaId = 1;

    // Mock dos objetos internos da GuiaEncaminhamento
    Ocs ocs = new Ocs();
    ocs.setRegistroAns("99999");
    ocs.setDescricao("Hospital Teste");
    ocs.setCnpj("12.345.678/0001-90");

    ProcedimentoMedico pm = new ProcedimentoMedico();
    pm.setTuss("40304321");
    pm.setDescricao("Hemograma Completo");

    GuiaPm gpm1 = new GuiaPm();
    gpm1.setPm(pm);
    gpm1.setPmQtd(2);
    gpm1.setValorUnitario(new BigDecimal("50.00")); // Valor total bruto = 100.00
    gpm1.setPosAuditoria(new BigDecimal("40.00"));  // Valor liquido = 40.00
    gpm1.setUnidadeMedida("01");

    GuiaPm gpm2 = new GuiaPm();
    gpm2.setPm(pm);
    gpm2.setPmQtd(1);
    gpm2.setValorUnitario(new BigDecimal("20.00")); // Valor total bruto = 20.00
    gpm2.setPosAuditoria(new BigDecimal("10.00"));  // Valor liquido = 10.00
    gpm2.setUnidadeMedida("01");
    
    Profissional prof = new Profissional();
    prof.setCpf("11111111111");
    prof.setNome("Dr. Silva");

    GuiaEncaminhamento guiaMock = new GuiaEncaminhamento();
    guiaMock.setOcs(ocs);
    guiaMock.setEmissaoData(LocalDate.now());
    guiaMock.setResponsavel(prof);
    guiaMock.setObservacao("Teste de emissao");
    guiaMock.setProcedimentos(List.of(gpm1, gpm2));

    // Vincula a referência bidirecional
    gpm1.setGuiaEncaminhamento(guiaMock);
    gpm2.setGuiaEncaminhamento(guiaMock);

    when(repository.findById(guiaId)).thenReturn(Optional.of(guiaMock));

    // --- 2. AÇÃO (ACT) ---
    GuiaFaturamento resultado = tissService.findById(guiaId);

    // --- 3. VALIDAÇÃO (ASSERT) ---
    assertNotNull(resultado);

    // Validações do Cabeçalho
    assertEquals("Operadora Teste", resultado.getCabecalho().getIdentificacaoOperadora().getNomeOperadora());
    assertEquals("123456", resultado.getCabecalho().getIdentificacaoOperadora().getNumeroRegistroANSOperadora());
    assertEquals("Hospital Teste", resultado.getCabecalho().getIdentificacaoPrestador().getNomePrestador());

    // Validações dos Procedimentos
    assertEquals(2, resultado.getProcedimentos().size());
    Procedimento proc = resultado.getProcedimentos().get(0);
    assertEquals("40304321", proc.getCodigoProcedimento());
    assertTrue(proc.getProcedimentoPrincipal()); // Primeiro item deve ser o principal (sequencial == 1)
    assertEquals(new BigDecimal("100.00"), proc.getValorTotal());

    // Validações dos Valores
    assertEquals(new BigDecimal("120.00"), resultado.getValores().getValorTotalBruto());
    assertEquals(new BigDecimal("50.00"), resultado.getValores().getValorTotalLiquido());
    assertEquals(new BigDecimal("70.00"), resultado.getValores().getValorTotalGlosa()); // 120 - 50 = 70

    // Validação da Forma de Pagamento
    assertEquals(1, resultado.getFormasPagamento().size());
    assertEquals("ONLINE", resultado.getFormasPagamento().get(0).getTipo());

    verify(repository, times(1)).findById(guiaId);
  }

  @Test
  @DisplayName("Deve retornar GuiaFaturamento vazia quando não encontrar a guia no banco")
  void findById_NaoEncontrado() {
    // ARRANGE
    Integer guiaId = 99;
    when(repository.findById(guiaId)).thenReturn(Optional.empty());

    // ACT
    GuiaFaturamento resultado = tissService.findById(guiaId);

    // ASSERT
    assertNotNull(resultado);
    // Exemplo: verificar se retornou a instância de empty() configurada na sua classe
    verify(repository, times(1)).findById(guiaId);
  }

  @Test
  @DisplayName("Deve tratar valores nulos e preencher padrões com 'N/I'")
  void findById_ComValoresNulos() {
    // ARRANGE
    Integer guiaId = 2;

    Ocs ocs = new Ocs(); // Registro ANS nulo
    ocs.setDescricao("Clínica Sem ANS");

    GuiaEncaminhamento guiaMock = new GuiaEncaminhamento();
    guiaMock.setOcs(ocs);
    guiaMock.setObservacao(null); // Observação nula
    guiaMock.setProcedimentos(Collections.emptyList());

    when(repository.findById(guiaId)).thenReturn(Optional.of(guiaMock));

    // ACT
    GuiaFaturamento resultado = tissService.findById(guiaId);

    // ASSERT
    assertEquals("N/I", resultado.getCabecalho().getIdentificacaoPrestador().getNumeroRegistroANSPrestador());
    assertEquals("N/I", resultado.getObservacoes());
    assertEquals(new BigDecimal("0.00"), resultado.getValores().getValorTotalBruto());
    assertEquals(new BigDecimal("0.00"), resultado.getValores().getValorTotalLiquido());
  }

}
