package br.com.wagnersoft.macedonia.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private GuiaEncaminhamentoRepository rep;

  public GuiaFaturamento findById(final Integer id) {
    return rep.findById(id)
        .map(this::convertNonNull)
        .orElseGet(GuiaFaturamento::empty);
  }

  private GuiaFaturamento convertNonNull(final GuiaEncaminhamento guia) {

    final Prestador prestador = Prestador.builder()
        .numeroRegistroANSPrestador(guia.getOcs().getRegistroAns() != null ? guia.getOcs().getRegistroAns() : "N/I")
        .nomePrestador(guia.getOcs().getDescricao())
        .cnpj(guia.getOcs().getCnpj())
        .build();

    final Operadora operadora = Operadora.builder()
        .nomeOperadora("N/I")
        .numeroRegistroANSOperadora("N/I")
        .build();

    final Cabecalho cab = Cabecalho.builder()
        .identificacaoPrestador(prestador)
        .identificacaoOperadora(operadora)
        .dataEmissao(guia.getEmissaoData())
        .build();

    final List<Procedimento> procedimentos = new ArrayList<>();

    BigDecimal totalBrutoAcumulado = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    final Valores valores = Valores.builder()
        .valorTotalGlosa(BigDecimal.ZERO)
        .valorTotalBruto(BigDecimal.ZERO)
        .valorTotalLiquido(BigDecimal.ZERO.setScale(2))
        .descontos(BigDecimal.ZERO)
        .build();

    int sequencial = 1;

    for (GuiaPm gpm : guia.getProcedimentos()) {

      logger.debug("{}", gpm);

      BigDecimal posAuditoria = gpm.getPosAuditoria() != null ? gpm.getPosAuditoria() : BigDecimal.ZERO;

      valores.setValorTotalLiquido(valores.getValorTotalLiquido().add(posAuditoria).setScale(2, RoundingMode.HALF_UP));

      logger.info("liquido Total = {}", valores.getValorTotalLiquido());

      BigDecimal quantidade = BigDecimal.valueOf(Long.valueOf(gpm.getPmQtd()));

      BigDecimal valorUnitario = gpm.getValorUnitario() != null ? gpm.getValorUnitario() : BigDecimal.ZERO;      

      BigDecimal valorTotalProc = valorUnitario.multiply(quantidade).setScale(2, RoundingMode.HALF_UP);

      Procedimento proc = Procedimento.builder()
          .sequencial(sequencial++)
          .codigoProcedimento(gpm.getPm().getTuss())
          .descricaoProcedimento(gpm.getPm().getDescricao())
          .tabela("TUSS")
          .quantidade(gpm.getPmQtd())
          .unidadeMedida(UnidadeMedidaEnum.getDescricaoByCodigo(gpm.getUnidadeMedida()).orElse("N/I"))
          .valorUnitario(valorUnitario)
          .valorTotal(valorTotalProc)
          .profissionalExecutante(guia.getResponsavel())
          .dataRealizacao(guia.getEmissaoData())
          .procedimentoPrincipal(false)
          .build();

      procedimentos.add(proc);
      totalBrutoAcumulado = totalBrutoAcumulado.add(valorTotalProc);
    };

    valores.setValorTotalBruto(totalBrutoAcumulado);

    valores.setValorTotalGlosa(valores.getValorTotalBruto().subtract(valores.getValorTotalLiquido()));

    final List<FormaPagamento> pagamentos = new ArrayList<>();

    pagamentos.add(FormaPagamento.builder()
        .tipo("ONLINE")
        .dataPagamento(LocalDate.now())
        .valorPago(guia.getValorTotal() != null ? guia.getValorTotal() : BigDecimal.ZERO)
        .build());

    return GuiaFaturamento.builder()
        .cabecalho(cab)
        .procedimentos(procedimentos)
        .valores(valores)
        .formasPagamento(pagamentos)
        .observacoes(guia.getObservacao() != null ? guia.getObservacao() : "N/I")
        .build();

  }

}
