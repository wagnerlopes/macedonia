package br.com.wagnersoft.macedonia.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wagnersoft.macedonia.model.GuiaEncaminhamento;
import br.com.wagnersoft.macedonia.repository.GuiaEncaminhamentoRepository;
import br.com.wagnersoft.macedonia.tiss.Cabecalho;
import br.com.wagnersoft.macedonia.tiss.FormaPagamento;
import br.com.wagnersoft.macedonia.tiss.GuiaFaturamento;
import br.com.wagnersoft.macedonia.tiss.Operadora;
import br.com.wagnersoft.macedonia.tiss.Prestador;
import br.com.wagnersoft.macedonia.tiss.Procedimento;
import br.com.wagnersoft.macedonia.tiss.Valores;
import br.com.wagnersoft.macedonia.type.UnidadeMedidaEnum;

@Service
public class TissService {

	private static final Logger logger = LoggerFactory.getLogger(TissService.class);

	@Autowired
	private GuiaEncaminhamentoRepository rep;

	public List<GuiaFaturamento> listAll() {
		final List<GuiaFaturamento> lista = new ArrayList<>();
		rep.findAll().forEach(e -> {lista.add(this.convert(e)); logger.info(e.toString());});
		return lista;
	}

	public GuiaFaturamento findById(Integer id) {
		final GuiaEncaminhamento guia = rep.findById(id).orElseThrow();
		return this.convert(guia);
	}

	private GuiaFaturamento convert(GuiaEncaminhamento guia) {
		final Prestador prestador = Prestador.builder()
				.numeroRegistroANSPrestador(guia.getOcs().getRegistroAns())
				.nomePrestador(guia.getOcs().getDescricao())
				.cnpj(guia.getOcs().getCnpj())
				.build();

		final Operadora operadora = Operadora.builder()
				.nomeOperadora("Saude Integrada S/A")
				.numeroRegistroANSOperadora("12345678")
				.build();
				
		final Cabecalho cab = Cabecalho.builder()
				.identificacaoPrestador(prestador)
				.identificacaoOperadora(operadora)
				.dataEmissao(guia.getEmissaoData())
				.build();

		final List<Procedimento> procedimentos = new ArrayList<>(guia.getProcedimentos().size());

		final Valores valores = Valores.builder()
				.valorTotalGlosa(BigDecimal.ZERO)
				.valorTotalBruto(guia.getValorTotal())
				.valorTotalLiquido(BigDecimal.ZERO.setScale(2))
				.descontos(BigDecimal.ZERO)
				.build();
		
		guia.getProcedimentos().forEach(g -> {

			logger.info("{}", g);

			valores.setValorTotalLiquido(valores.getValorTotalLiquido().add(g.getPosAuditoria()).setScale(2));

			logger.info("liquido Total = {}", valores.getValorTotalLiquido());

			final Procedimento proc = Procedimento.builder()
					.sequencial(1)
					.codigoProcedimento(g.getPm().getTuss())
					.descricaoProcedimento(g.getPm().getDescricao())
					.tabela("TUSS")
					.quantidade(g.getPmQtd())
					.unidadeMedida(UnidadeMedidaEnum.getDescricaoByCodigo(g.getUnidadeMedida()).orElse("N/I"))
					.valorUnitario(g.getValorUnitario())
					.valorTotal(g.getValorUnitario().multiply(BigDecimal.valueOf(Long.valueOf(g.getPmQtd()))).setScale(2))
					.profissionalExecutante(guia.getResponsavel())
					.dataRealizacao(guia.getEmissaoData())
					.procedimentoPrincipal(false)
					.build();
			
			procedimentos.add(proc);
		});

		valores.setValorTotalGlosa(valores.getValorTotalBruto().subtract(valores.getValorTotalLiquido()));
		
		final List<FormaPagamento> pagamentos = new ArrayList<>();
		
		final FormaPagamento pag = FormaPagamento.builder()
				.tipo("ONLINE")
				.dataPagamento(LocalDate.now())
				.valorPago(guia.getValorTotal())
				.build();

		pagamentos.add(pag);
		
		final GuiaFaturamento guiaFaturamento = GuiaFaturamento.builder()
				.cabecalho(cab)
				.procedimentos(procedimentos)
				.valores(valores)
				.formasPagamento(pagamentos)
				.observacoes(guia.getObservacao())
				.build();
		return guiaFaturamento;
	}

}
