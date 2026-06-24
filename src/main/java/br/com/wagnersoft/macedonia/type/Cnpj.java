package br.com.wagnersoft.macedonia.type;

import java.util.regex.Pattern;

public class Cnpj {

  private static final Pattern PATTERN = Pattern.compile("^\\d{3}.?\\d{3}.?\\d{3}/?\\d{4}-?\\d{2}$");

  public boolean isCnpj(String cnpj) {
    return cnpj != null && PATTERN.matcher(cnpj).matches();
  }

  public static String format(String cnpj) {

    if (cnpj == null || cnpj.isBlank()) return "CNPJ XX.XXX.XXX/XXXX-XX";

    String digits = cnpj.replaceAll("\\D", "");
    if (digits.length() != 14) return "CNPJ XX.XXX.XXX/XXXX-XX";

    return digits.substring(0,2) + "." +
    digits.substring(2,5) + "." + 
    digits.substring(5,8) + "/" +
    digits.substring(8,12) + "-" +
    digits.substring(12,14);
  }

}
