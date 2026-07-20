package br.com.wagnersoft.macedonia.util;

/**
 * Utilitário de validação e formatação de código TUSS.
 * 
 * <p> Terminologia Unificada da Saúde Suplementar (TUSS).</p>
 * 
 * @since 1.0
 * @version 1.0
 * @author Wagner Lopes
 */
public final class TussHelper {

  TussHelper() {
    // Protected
  }

  public static boolean isValid(final String codigo) {
    return codigo != null && codigo.length() == 8 && LuhnCheck.generate(codigo.substring(0, 7)) == Integer.valueOf(codigo.substring(7));
  }

  public static String format(String codigo) {

    if (codigo == null || codigo.isBlank()) return "XX.XX.XXX-X";

    String digits = codigo.replaceAll("\\D", "");
    if (digits.length() != 8) return "XX.XX.XXX-X";

    return
        codigo.substring(0,2) + "." +
        codigo.substring(2,4) + "." +
        codigo.substring(4,7) + "-" +
        codigo.substring(7);
  }

}
