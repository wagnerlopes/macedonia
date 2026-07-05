package br.com.wagnersoft.macedonia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.model.GuiaPm;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.tiss.Cabecalho;
import br.com.wagnersoft.macedonia.tiss.FormaPagamento;
import br.com.wagnersoft.macedonia.tiss.GuiaFaturamento;
import br.com.wagnersoft.macedonia.tiss.Operadora;
import br.com.wagnersoft.macedonia.tiss.Prestador;
import br.com.wagnersoft.macedonia.tiss.Procedimento;
import br.com.wagnersoft.macedonia.tiss.Valores;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;

/** TISS API service.
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
@Service
public class TissService {

  private static final Logger logger = LoggerFactory.getLogger(TissService.class);

  @Value("${app.operadora}")
  private String operadora;

  @Value("${app.registroAns}")
  private String registroAns;

  @Autowired
  private GuiaEncaminhamentoRepository rep;

  public GuiaFaturamento findById(final Integer id) {
    return rep.findById(id)
        .map(this::convertNonNull)
        .orElseGet(GuiaFaturamento::empty);
  }

  /** Converte a Guia de ENcaminhamento em Guia de Faturamento no padrao TISS.
   * @param guia Guia de Encaminhamento
   * @return Guia de Faturamento
   */
  private GuiaFaturamento convertNonNull(final GuiaEncaminhamento guia) {

    logger.debug("{}", guia);

    final Prestador prestador = Prestador.builder()
        .numeroRegistroANSPrestador(guia.getOcs().getRegistroAns() != null ? guia.getOcs().getRegistroAns() : "N/I")
        .nomePrestador(guia.getOcs().getDescricao())
        .cnpj(guia.getOcs().getCnpj())
        .build();

    final Operadora operadora = Operadora.builder()
        .nomeOperadora(this.operadora)
        .numeroRegistroANSOperadora(this.registroAns)
        .build();

    final Cabecalho cab = Cabecalho.builder()
        .identificacaoPrestador(prestador)
        .identificacaoOperadora(operadora)
        .dataEmissao(guia.getEmissaoData())
        .build();

    // Conversao de List<GuiaPm> em List<Procedimento>
    List<Procedimento> procedimentos = IntStream.range(0, guia.getProcedimentos().size())
        .mapToObj(i -> toProcedimento(guia.getProcedimentos().get(i), i + 1))
        .collect(Collectors.toList());

    // Calculo dos Valores da Guia
    BigDecimal bruto = calcularValorTotalBruto(procedimentos);

    BigDecimal liquido = calcularValorTotalLiquido(guia.getProcedimentos());

    BigDecimal glosa = bruto.subtract(liquido);

    Valores valores = Valores.builder()
        .valorTotalBruto(bruto)
        .valorTotalLiquido(liquido)
        .valorTotalGlosa(glosa)
        .descontos(BigDecimal.ZERO)
        .build();

    final List<FormaPagamento> pagamentos = new ArrayList<>();

    pagamentos.add(FormaPagamento.builder()
        .tipo("ONLINE")
        .dataPagamento(LocalDate.now())
        .valorPago(liquido != null ? liquido : BigDecimal.ZERO)
        .build());

    // Montagem da Guia de Faturamento
    return GuiaFaturamento.builder()
        .cabecalho(cab)
        .procedimentos(procedimentos)
        .valores(valores)
        .formasPagamento(pagamentos)
        .observacoes(guia.getObservacao() != null ? guia.getObservacao() : "N/I")
        .build();

  }  

  /** Converte a entidade {@link GuiaPm} em {@link Procedimento}.
   * @param gpm
   * @param sequencial
   * @return {@link Procedimento} Procedimento no padrao TISS
   */
  private Procedimento toProcedimento(final GuiaPm gpm, int sequencial) {

    logger.debug("{} - {}", sequencial, gpm);

    BigDecimal quantidade = BigDecimal.valueOf(Optional.ofNullable(gpm.getPmQtd()).orElse(0));

    BigDecimal valorUnitario = Optional.ofNullable(gpm.getValorUnitario()).orElse(BigDecimal.ZERO);

    BigDecimal valorTotal = valorUnitario.multiply(quantidade).setScale(2, RoundingMode.HALF_UP);

    return Procedimento.builder()
        .codigoProcedimento(gpm.getPm().getTuss())
        .dataRealizacao(gpm.getGuiaEncaminhamento().getEmissaoData())
        .descricaoProcedimento(gpm.getPm().getDescricao())
        .procedimentoPrincipal(sequencial == 1 ? true : false)
        .profissionalExecutante(gpm.getGuiaEncaminhamento().getResponsavel())
        .quantidade(gpm.getPmQtd())
        .sequencial(sequencial)
        .tabela("TUSS")
        .unidadeMedida(UnidadeMedidaEnum.getDescricaoByCodigo(gpm.getUnidadeMedida()).orElse("N/I"))
        .valorTotal(valorTotal)
        .valorUnitario(valorUnitario)
        .build();
  }

  /** Calculo do Valor Total Bruto, soma dos valores parciais dos procedimentos.
   * @param procs
   * @return Valor Bruto Total
   */
  private BigDecimal calcularValorTotalBruto(final List<Procedimento> procs) {
    return procs.stream()
        .map(Procedimento::getValorTotal)
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.HALF_UP);
  }  

  /** Calculo do Valor Total Liquido, soma dos valores pos-auditoria dos procedimentos.
   * @param procs
   * @return Valor Liquido Total
   */
  private BigDecimal calcularValorTotalLiquido(final List<GuiaPm> itens) {
    return itens.stream()
        .map(i -> Optional.ofNullable(i.getPosAuditoria()).orElse(BigDecimal.ZERO))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.HALF_UP);
  }  

}
