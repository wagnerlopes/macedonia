package br.com.wagnersoft.macedonia.model;

public enum Acomodacao {

	SUITE_INDIVIDUAL("Suíte individual"), 
	QUARTO_INDIVIDUAL("Quarto individual"), 
	ENFERMARIA_6("Enfermaria 6 leitos"), 
	ENFERMARIA_8("Enfermaria 8 leitos");

	public static final Acomodacao[] ALL = { SUITE_INDIVIDUAL, QUARTO_INDIVIDUAL, ENFERMARIA_6, ENFERMARIA_8 };

	private final String name;

	public static Acomodacao forName(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("Nome não pode ser nulo para acomodação");
		}
		if (name.toUpperCase().equals("Suíte individual")) {
			return SUITE_INDIVIDUAL;
		} else if (name.toUpperCase().equals("Quarto individual")) {
			return QUARTO_INDIVIDUAL;
		} else if (name.toUpperCase().equals("Enfermaria 6 leitos")) {
			return ENFERMARIA_6;
		} else if (name.toUpperCase().equals("Enfermaria 8 leitos")) {
			return ENFERMARIA_8;
		}
		throw new IllegalArgumentException("Nome \"" + name + "\" não corresponde a nenhuma acomodação");
	}

	private Acomodacao(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return getName();
	}	

}
