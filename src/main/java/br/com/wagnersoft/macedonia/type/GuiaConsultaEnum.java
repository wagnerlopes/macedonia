package br.com.wagnersoft.macedonia.type;

public enum GuiaConsultaEnum {

  AMBULATORIAL("02", "Guia de Procedimentos Ambulatoriais"),
  ALTA_COMPLEXIDADE("13", "Guia de Procedimentos de Alta Complexidade"),
  CONSULTA("01", "Guia de Consulta"),
  DOMICILIAR("09", "Guia de Atendimento Domiciliar"),
  EMERGENCIA("11", "Guia de Emergência"),
  EXAMES("06", "Guia de Exames"),
  HEMODINAMICA("05", "Guia de Hemodinâmica / Hemoterapia"),
  INTERNACAO("03", "Guia de Internação"),
  FISIOTERAPIA("07", "Guia para Terapia Ocupacional / Fisioterapia / Psicoterapia"),
  RADIOTERAPIA("14", "Guia de Radioterapia / Quimioterapia"),
  TELECONSULTA("10", "Guia de Teleconsulta / Teleatendimento"),
  TERAPIA("08", "Guia de Terapia (Sessões)"),
  TRANSPLANTE("12", "Guia de Transplante"),
  URGENCIA("04", "Guia de Atendimento Ambulatorial de Urgência/Pronto Socorro"),
  OUTROS("15", "Outros");

  private final String codigo;

  private final String descricao;

  private GuiaConsultaEnum(final String codigo, final String descricao) {
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
