package br.com.wagnersoft.macedonia.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum UnidadeMedidaEnum {

	DIARIA("01", "Diária"),
	HORA("02", "Hora"),
	PROCEDIMENTO("03", "Procedimento"),
	SESSAO("04", "Sessão"),
	USO("05", "Uso");

	private final String codigo;

	private final String descricao;

	public static final UnidadeMedidaEnum[] ALL = { DIARIA, HORA,PROCEDIMENTO, SESSAO, USO };

	private static final Map<String, UnidadeMedidaEnum> LOOKUP = new HashMap<>();

	static {
		for (UnidadeMedidaEnum unidade : UnidadeMedidaEnum.values()) {
			LOOKUP.put(unidade.getCodigo(), unidade);
		}
	}
	
	private UnidadeMedidaEnum(final String codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static Optional<UnidadeMedidaEnum> fromCodigo(String codigo) {
        return Optional.ofNullable(LOOKUP.get(codigo));
    }

    public static Optional<String> getDescricaoByCodigo(String codigo) {
        return fromCodigo(codigo).map(UnidadeMedidaEnum::getDescricao);
    }
    
	@Override
	public String toString() {
		return getDescricao();
	}	

}
