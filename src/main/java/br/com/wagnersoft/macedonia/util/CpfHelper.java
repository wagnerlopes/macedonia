package br.com.wagnersoft.macedonia.util;

import java.util.regex.Pattern;

/**
 *  CPF Utility.
 * TODO: renomear
 * @author Wagner Lopes
 * @since 1.0
 * @version 1.0
 */
public final class CpfHelper {

  private static final Pattern PATTERN = Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

  CpfHelper() {
    // Protected
  }
  
  public static boolean isCpf(String cpf) {
    return cpf != null && PATTERN.matcher(cpf).matches();
  }

  public static String format(String cpf) {

    if (cpf == null || cpf.isBlank()) return "CPF XXX.XXX.XXX-XX";

    String digits = cpf.replaceAll("\\D", "");
    if (digits.length() != 11) return "CPF XXX.XXX.XXX-XX";

    return digits.substring(0,3) + "." +
    digits.substring(3,6) + "." + 
    digits.substring(6,9) + "-" +
    digits.substring(9,11);
  }

}
