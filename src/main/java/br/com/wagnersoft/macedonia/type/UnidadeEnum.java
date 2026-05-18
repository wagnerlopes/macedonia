package br.com.wagnersoft.macedonia.type;

public enum UnidadeEnum {

	DIARIA("01", "Diária"),
	HORA("02", "Hora"),
	PROCEDIMENTO("03", "Procedimento"),
	SESSAO("04", "Sessão"),
	USO("05", "Uso");
	
	private final String codigo;

	private final String descricao;

	public static final UnidadeEnum[] ALL = { DIARIA, HORA,PROCEDIMENTO, SESSAO, USO };

	private UnidadeEnum(final String codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return this.codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	@Override
	public String toString() {
		return getDescricao();
	}	

}
