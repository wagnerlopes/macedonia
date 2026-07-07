package br.com.wagnersoft.macedonia.type;

public enum AcomodacaoEnum {

  ACOMODACAO_ACOMPANHANTE("14", "Acomodação para acompanhante"),
  APARTAMENTO("02", "Apartamento (quarto privativo, sem suíte diferenciada)"),
  APTO_INDIVIDUAL("03", "Apartamento Individual (quarto privativo com banheiro)"),
  APTO_LUXO("04", "Apartamento VIP / Apartamento de Luxo"),
  APTO_MATERNIDADE("06", "Apartamento Maternidade (sala de parto/acomodação mãe-bebê)"),
  APTO_SEMI_PRIVATIVO("05", "Apartamento Semi-privativo (até 2 leitos)"),
  BERCARIO("11", "Berçário"),
  CUIDADOS_PALIATIVOS("13", "Cuidados Paliativos"),
  ENFERMARIA("01", "Enfermaria (leito coletivo)"),
  SALA_RECUPERACAO("12", "Sala de Recuperação Pós-anestésica (SRPA)"),
  UTI("07", "Unidade de Terapia Intensiva (UTI)"),
  UTI_CUIDADOS("10", "Unidade de Cuidados Intermediários (UTCI)"),
  UTI_NEONATAL("08", "Unidade de Terapia Intensiva Neonatal (UTIN)"),
  UTI_PEDIATRICA("09", "Unidade de Terapia Intensiva Pediátrica (UTIP)"),
  OUTROS("15", "Outros");

  private final String codigo;

  private final String descricao;

  private AcomodacaoEnum(final String codigo, final String descricao) {
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
