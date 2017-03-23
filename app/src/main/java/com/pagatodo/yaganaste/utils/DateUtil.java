package com.pagatodo.yaganaste.utils;

/**
 * Created by flima on 23/02/2017.
 */

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Fausto on 6/29/16.
 */
public class DateUtil {

    public static String simpleDateFormat = "yyyy/MM/dd";

    public static DatePickerDialog getMaterialDatePicker(DatePickerDialog.OnDateSetListener callback) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        return dpd;
    }

    public static TimePickerDialog getMaterialTimePicker(TimePickerDialog.OnTimeSetListener callback) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                callback,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        return tpd;
    }

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
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormat, Locale.US);
        String result = dateFormat.format(date.getTime());
        return result;
    }

}