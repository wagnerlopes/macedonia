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
import br.com.wagnersoft.macedonia.type.Cabecalho;
import br.com.wagnersoft.macedonia.type.FormaPagamento;
import br.com.wagnersoft.macedonia.type.GuiaFaturamento;
import br.com.wagnersoft.macedonia.type.Operadora;
import br.com.wagnersoft.macedonia.type.Prestador;
import br.com.wagnersoft.macedonia.type.Procedimento;
import br.com.wagnersoft.macedonia.type.Valores;

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

		final List<Procedimento> procedimentos = new ArrayList<>(guia.getGuiaOcsPm().size());

		final BigDecimal valorLiquido = BigDecimal.ZERO;
		
		guia.getGuiaOcsPm().forEach(g -> {

			valorLiquido.add(g.getPosAuditoria());
			
			final Procedimento proc = Procedimento.builder()
					.sequencial(1)
					.codigoProcedimento(g.getOcsPm().getPm().getTuss())
					.descricaoProcedimento(g.getOcsPm().getPm().getDescricao())
					.tabela("TUSS")
					.quantidade(g.getPmQtd())
					.unidadeMedida(g.getOcsPm().getUnidadeMedida())
					.valorUnitario(g.getOcsPm().getValorUnitario())
					.valorTotal(g.getOcsPm().getValorUnitario().multiply(BigDecimal.valueOf(Long.valueOf(g.getPmQtd()))))
					.profissionalExecutante(guia.getResponsavel())
					.dataRealizacao(guia.getEmissaoData())
					.procedimentoPrincipal(false)
					.build();
			
			procedimentos.add(proc);
		});

		final Valores val = Valores.builder()
				.valorTotalGlosa(guia.getValorTotal().subtract(valorLiquido))
				.valorTotalBruto(guia.getValorTotal())
				.valorTotalLiquido(valorLiquido)
				.descontos(BigDecimal.ZERO)
				.build();

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
				.valores(val)
				.formasPagamento(pagamentos)
				.observacoes("Teste pagamento guia de encaminhamento")
				.build();
		return guiaFaturamento;
	}

}
