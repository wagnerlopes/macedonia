package br.com.wagnersoft.macedonia.type;

import java.util.regex.Pattern;

public class Cnpj {

	private static final Pattern PATTERN = Pattern.compile("^\\d{3}.?\\d{3}.?\\d{3}/?\\d{4}-?\\d{2}$");

	public boolean isCnpj(String cnpj) {
		return PATTERN.matcher(cnpj).matches();
	}
	
    public static String format(String cnpj) {
      return cnpj.isEmpty() ? "CNPJ XXX.XXX.XXX/XXXX-XX" :
    	  cnpj.substring(0,2) + "." +
          cnpj.substring(2,5) + "." + 
          cnpj.substring(5,8) + "/" +
          cnpj.substring(8,12) + "-" +
          cnpj.substring(12);
    }

}
