package jeff.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Util {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale localeBR = new Locale("pt", "BR");
    //private static final NumberFormat inteiro = NumberFormat.getInstance();
    private static final NumberFormat dinheiro = NumberFormat.getCurrencyInstance(localeBR);
    //private static final NumberFormat percentual = NumberFormat.getPercentInstance(localeBR);
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance(localeBR);

    public static LocalDate parseLocalDate(String date) {
        return java.time.LocalDate.parse(date, formatter);
    }

    public static String parseString(LocalDate date) {
        return date.format(formatter).toString();
    }

    public static String toStringDinheiro(Double valor) {
        return String.valueOf(dinheiro.format(valor));
    }

    public static double toDoubleNumber(String valor) {
        numberFormat.setParseIntegerOnly(false);
        numberFormat.setMaximumFractionDigits(2);
        double number = 0.0;
        try {
            number = numberFormat.parse(valor).doubleValue();
            valor = numberFormat.format(number);
            number = numberFormat.parse(valor).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return number;
    }
}