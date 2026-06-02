package br.com.wagnersoft.macedonia;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String source) {
        if (source == null) return null;
        String s = source.trim();
        if (s.isEmpty()) return null;

        // Remove tudo exceto dígitos, vírgula, ponto e sinal
        s = s.replaceAll("[^0-9,\\.-]", "");

        // Se contém ponto e vírgula: assume ponto = milhares, vírgula = decimal (pt-BR)
        if (s.contains(".") && s.contains(",")) {
            s = s.replace(".", "");
            s = s.replace(",", ".");
        } else if (s.contains(",")) {
            // apenas vírgula -> decimal separator
            s = s.replace(",", ".");
        }
        // se só tem pontos, pode ser decimal ou milhares; assumimos ponto decimal
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
