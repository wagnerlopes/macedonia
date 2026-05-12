package br.com.wagnersoft.macedonia.model;

public enum Acomodacao {

	SUITE_INDIVIDUAL(1, "Suíte individual"),
	QUARTO_INDIVIDUAL(2, "Quarto individual"),
	ENFERMARIA_6(3, "Enfermaria 6 leitos"),
	ENFERMARIA_8(4, "Enfermaria 8 leitos");

	public static final Acomodacao[] ALL = { SUITE_INDIVIDUAL, QUARTO_INDIVIDUAL, ENFERMARIA_6, ENFERMARIA_8 };

	private final int codigo;

	private final String descricao;

	public static Acomodacao forName(final String descricao) {
		if (descricao == null) {
			throw new IllegalArgumentException("Nome não pode ser nulo para acomodação");
		}
		if (descricao.toUpperCase().equals("Suíte individual")) {
			return SUITE_INDIVIDUAL;
		} else if (descricao.toUpperCase().equals("Quarto individual")) {
			return QUARTO_INDIVIDUAL;
		} else if (descricao.toUpperCase().equals("Enfermaria 6 leitos")) {
			return ENFERMARIA_6;
		} else if (descricao.toUpperCase().equals("Enfermaria 8 leitos")) {
			return ENFERMARIA_8;
		}
		throw new IllegalArgumentException("Descrição \"" + descricao + "\" não corresponde a nenhuma acomodação");
	}

	private Acomodacao(final int codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
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
