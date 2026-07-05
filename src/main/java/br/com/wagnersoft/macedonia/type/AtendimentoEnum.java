package br.com.wagnersoft.macedonia.type;

public enum AtendimentoEnum {

  CONSULTA("01", "Consulta"),
  RETORNO("02", "Retorno"),
  EXAME("03", "Exame"),
  PROCEDIMENTO("04", "Procedimento"),
  URGENCIA("05", "Urgência"),
  EMERGENCIA("06", "Emergência"),
  INTERNACAO("07", "Internação"),
  PRONTO_ATENDIMENTO("08", "Pronto Atendimento"),
  DOMILICIAR("09", "Atendimento Domiciliar"),
  TELECONSULTA("10", "Teleconsulta"),
  TERAPIA("11", "Terapia/Sessão"),
  ACOMPANHAMENTO("12", "Acompanhamento"),
  VACINACAO("13", "Vacinação"),
  TRIAGEM("14", "Triagem"),
  OUTROS("15", "Outros");

  private final String codigo;

  private final String descricao;

  public static final AtendimentoEnum[] ALL = {
      ACOMPANHAMENTO, CONSULTA, DOMILICIAR,	EMERGENCIA,	EXAME, INTERNACAO, PROCEDIMENTO, PRONTO_ATENDIMENTO,
      RETORNO, TELECONSULTA, TERAPIA, TRIAGEM, URGENCIA, VACINACAO, OUTROS
  };

  private AtendimentoEnum(final String codigo, final String descricao) {
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
