package com.pagatodo.yaganaste.utils;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Juan Guerra on 27/03/2017.
 */

public class StringUtils {

    private static final int SHORT_MONTH_NAME_L = 3;

    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public static String getMonthShortName(@Nullable String monthLongName) {
        if (monthLongName == null) {
            return "";
        } else if (monthLongName.length() <= SHORT_MONTH_NAME_L) {
            return monthLongName;
        }
        return monthLongName.substring(0, SHORT_MONTH_NAME_L);
    }

    public static String getCurrencyValue(double value) {
        return getCurrencyValueInFormat(value, 2, 1);
    }

    public static String getCurrencyValue(String value) {
        return getCurrencyValue(getDoubleValue(value));
    }

    private static String getCurrencyValueInFormat(double value, int minFractionDigits, int minIntDigits) {
        Locale mx = Locale.getDefault();
        Locale.setDefault(Locale.US);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        formatter.setNegativePrefix("-$");
        formatter.setNegativeSuffix("");
        formatter.setPositivePrefix("$");
        formatter.setPositiveSuffix("");
        formatter.setMinimumFractionDigits(minFractionDigits);
        formatter.setMaximumFractionDigits(minFractionDigits);
        formatter.setMinimumIntegerDigits(minIntDigits);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        Locale.setDefault(mx);
        return formatter.format(value);
    }

    public static String getCurrencyNoFraction(String value){
        return getCurrencyNoFraction(getDoubleValue(value));
    }

    public static String getCurrencyNoFraction(double value){
        return getCurrencyValueInFormat(value, 0, 1);
    }

    public static Double getDoubleValue(String value) {
        try {
            value = value.replace("$", "").replace(",", "").replace("%", "")
                    .replace("&nbsp;", "");
            if (value.isEmpty()) {
                return 0.0;
            }
            return Double.valueOf(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static Double getDoubleValue(TextView textView) {
        return getDoubleValue(textView.getText().toString());
    }

    public static int getIntValue(String value){
        return getDoubleValue(value).intValue();
    }

    public static SpannableStringBuilder getSpannable (String formatted) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(formatted);
        return spannableStringBuilder;
    }


    public static String getCreditCardFormat(String card){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < card.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }
            result.append(card.charAt(i));
        }

        return result.toString();
    }

    public static String createTicket() {
        return String.format("%1$tY%1$tM%1$tS%2$02d", Calendar.getInstance(), (int) (Math.random() * 90));
    }

    public static String ocultarCardNumber(String cardNumber) {
        String lastFourNumbers = cardNumber.substring(cardNumber.length() - 4);
        String comodines = "****";
        return  comodines + comodines + comodines + lastFourNumbers;
    }
}
