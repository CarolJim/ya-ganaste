package com.pagatodo.yaganaste.utils;

/**
 * Created by flima on 23/02/2017.
 */

import com.pagatodo.yaganaste.data.dto.AdquirentePaymentsTab;
import com.pagatodo.yaganaste.data.dto.MonthsMovementsTab;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Fausto on 6/29/16.
 */
public class DateUtil {

    public static String simpleDateFormatCustom = "dd MMM yyyy";
    public static String completeDateFormat = "dd MMM yyyy hh:mm:ss";
    public static String simpleDateFormatFirstYear = "yyyy-MM-dd";




    public static String renameDateMonth(String fullDateName) {
        char[] chars = fullDateName.toCharArray();
        for (int x = 0; x < chars.length; x++) {
            if (Character.isLetter(chars[x])) {
                chars[x] = Character.toUpperCase(chars[x]);
                return new String(chars).replace(".", "");
            }
        }
        return fullDateName.replace(".", "");
    }


    public static String getBirthDateCustomString(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormatCustom, new Locale("es", "mx"));
        return renameDateMonth(dateFormat.format(date.getTime()));

    }


    public static String getTodayCompleteDateFormat() {
        return getCompleteDateFormat(Calendar.getInstance());
    }


    public static String getCompleteDateFormat(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(completeDateFormat, new Locale("es"));
        String result = dateFormat.format(date.getTime());
        return renameDateMonth(result);
    }


    public static String getDateStringFirstYear(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormatFirstYear, Locale.US);
        String result = dateFormat.format(date.getTime());
        return result;
    }

    public static List<MonthsMovementsTab> getLastMovementstMonths() {

        // Calendar calendar = Calendar.getInstance(new Locale("MX"));
        LinkedList<MonthsMovementsTab> names = new LinkedList<>();

        for (int subs = 0; subs >= -4; subs--) {
            Calendar calendar = Calendar.getInstance(new Locale("MX"));
            calendar.add(Calendar.MONTH, subs);
            names.addFirst(new MonthsMovementsTab(getMonthShortName(calendar), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)));
        }
        return names;
    }

    public static String getMonthShortName(Calendar calendar) {
        return StringUtils.capitalize(StringUtils.getMonthShortName(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "MX"))));
    }


    public static List<AdquirentePaymentsTab> getTabsAdquirente() {


        LinkedList<AdquirentePaymentsTab> tabs = new LinkedList<>();

        for (int subs = 0; subs >= -4; subs--) {
            Calendar calendar = Calendar.getInstance(new Locale("MX"));
            DateFormat dateFormat = new SimpleDateFormat("MM-yyyy", Locale.US);
            calendar.add(Calendar.MONTH, subs);
            tabs.addFirst(new AdquirentePaymentsTab(getMonthShortName(calendar), dateFormat.format(calendar.getTime())));
        }

        return tabs;

    }

    public static Date getAdquirenteMovementDate(String movDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm", new Locale("es", "mx"));
        Date date = null;
        try {
            date = dateFormat.parse(movDate);
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("dd MMM yyyy  hh:mm", new Locale("es", "mx"));
            try {
                date = dateFormat.parse(movDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return date;
    }


}
