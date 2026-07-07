package br.com.wagnersoft.macedonia.type;

import java.util.Arrays;

public enum EstabelecimentoSaudeEnum {

  HOSPITAL_GERAL("01", "Hospital geral"),
  HOSPITAL_ESPECIALIZADO("02", "Hospital especializado"),
  HOSPITAL_ENSINO("03", "Hospital ensino"),
  HOSPITAL_DIA("04", "Hospital dia"),
  CENTRO_REFERENCIA("05", "Centro de referência"),
  CLINICA_MEDICA("06", "Clínica médica / ambulatório geral"),
  CLINICA_ESPECIALIZADA("07", " Clínica especializada"),
  UPA("08", "Unidade de pronto atendimento (UPA)"),
  AMBULATORIO_CIRURGICO("09", "Centro cirúrgico ambulatorial "),
  DIAGNOSTICO_IMAGEM("10", "Serviço de diagnóstico por imagem / radiologia"),
  LABORATORIO("11", "Laboratório de análises clínicas"),
  CLINICA_ODONTOLOGICA("12", "Clínica odontológica"),
  UTI("13", "Unidade de terapia intensiva (UTI)"),
  CASA_REPOUSO("14", "Casa de repouso / cuidado continuado"),
  CENTRO_FISIOTERAPIA("15", "Centro de fisioterapia / reabilitação"),
  CENTRO_VACINACAO("16", "Centro de fisioterapia / reabilitação"),
  SERVICO_HEMODIALISE("17", "Serviço de hemodiálise"),
  UBS("18", "Unidade Básica de Saúde"),
  APOIO_LOGISTICO("19", "Serviço de apoio logístico"),
  OUTROS("20", "Outros serviços de atenção especializada");

  private final String codigo;

  private final String descricao;

  private EstabelecimentoSaudeEnum(final String codigo, final String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public String getDescricao() {
    return this.descricao;
  }

  public static final String fromCodigo(String codigo) {
    return Arrays.stream(EstabelecimentoSaudeEnum.values())
        .filter(e -> e.getCodigo().equals(codigo))
        .map(EstabelecimentoSaudeEnum::getDescricao)
        .findFirst()
        .orElse("");
  }

  @Override
  public String toString() {
    return getDescricao();
  }	

}
