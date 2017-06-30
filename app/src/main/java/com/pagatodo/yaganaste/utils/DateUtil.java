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

    public static String simpleDateFormat = "dd/MMM/yyyy";
    public static String simpleDateFormatCustom = "dd/MM/yyyy";
    public static String completeDateFormat = "dd/MMM/yyyy hh:mm:ss";
    public static String simpleDateFormatFirstYear = "yyyy-MM-dd";
    public static String screenShotDateFormat = "yyyy-MM-dd_hh:mm:ss";


    public static String formatDate(int dayOfMonth, int monthOfYear, int year) {

        String day = "";
        String month = "";
        int iMonth = ++monthOfYear;
        if (dayOfMonth < 10) {
            day = String.format("0%s", dayOfMonth);
        } else {
            day = String.format("%s", dayOfMonth);
        }

        if (iMonth < 10) {
            month = String.format("0%s", iMonth);
        } else {
            month = String.format("%s", iMonth);
        }

        String date = String.format("%s/%s/%s", day, month, year);

        return date;

    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static Calendar getCalendarYearsAgo(int yearsAgo) {
        Calendar c = Calendar.getInstance();

        c.set(c.get(Calendar.YEAR) - yearsAgo, c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        return c;
    }

    public static int getYearsDiffFromNow(Date last) {
        Calendar a = getCalendar(last);
        Calendar b = getCalendar(new Date());
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Date getDateFromString(String dateAsString) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddd/MM/yyyy");
        Date date = null;
        try {

            date = sdf.parse(dateAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }


    public static String getBirthDateString(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormat, new Locale("es"));
        String result = dateFormat.format(date.getTime());
        return result;
    }

    public static String getBirthDateCustomString(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormatCustom, new Locale("es"));
        String result = dateFormat.format(date.getTime());
        return result;
    }


    public static String getTodayCompleteDateFormat() {
        return getCompleteDateFormat(Calendar.getInstance());
    }


    public static String getCompleteDateFormat(Calendar date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(completeDateFormat, new Locale("es"));
        String result = dateFormat.format(date.getTime());
        return result;
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

    public static AdquirentePaymentsTab getTabAdquirente() {


        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy", Locale.US);
        Date date = new Date();
        String formatDate = dateFormat.format(date);


        return new AdquirentePaymentsTab("", formatDate);
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy hh:mm", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(movDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getEmisorMovementDate(String movDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(movDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getEmisorMovementTime(String movDate) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(movDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
