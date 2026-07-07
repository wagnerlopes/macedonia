package br.com.wagnersoft.macedonia.type;

public enum ConsultaEnum {

  ACOMPANHAMENTO("02", "Consulta de acompanhamento"),
  COLETIVA("11", "Consulta coletiva / em grupo"),
  DOMICILIAR("04", "Consulta domiciliar"),
  EMERGENCIA("03", "Consulta de emergência"),
  INICIAL("01", "Consulta inicial"),
  MULTIPROFISSIONAL("06", "Consulta multiprofissional"),
  PERICIAL("12", "Consulta administrativa / avaliação pericial"),
  PRE_NATAL("08", "Consulta pré-natal"),
  PUERPERAL("09", "Consulta puerperal"),
  RETORNO("07", "Consulta de retorno (pós-procedimento)"),
  TELECONSULTA("05", "Teleconsulta / Consulta remota"),
  TRIAGEM("13", "Consulta de triagem"),
  URGENCIA("10", "Consulta de urgência"),
  VACINACAO("14", "Consulta de triagem para vacinação"),
  OUTROS("15", "Outros");

  private final String codigo;

  private final String descricao;

  private ConsultaEnum(final String codigo, final String descricao) {
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
