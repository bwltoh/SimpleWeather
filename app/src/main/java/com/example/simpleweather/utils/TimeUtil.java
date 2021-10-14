package com.example.simpleweather.utils;


import android.content.res.Resources;
import android.text.format.DateUtils;

import com.example.simpleweather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static String getDate(Resources resources, long dateInMilli) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM", getLocale(resources));
        Date date = new Date();
        date.setTime(dateInMilli * 1000);
        return simpleDateFormat.format(date);
    }

    public static String getDateWithDayName(Resources resources, long dateInMilli) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE.MM.yyyy", getLocale(resources));
        Date date = new Date(dateInMilli * 1000);

        return simpleDateFormat.format(date);
    }

    public static String getDayName(Resources resources, long dateInMilli) {

        if (isToday(dateInMilli * 1000)) {
            return resources.getString(R.string.today);
        } else if (isTomorrow(dateInMilli * 1000)) {
            return resources.getString(R.string.tomorow);
        } else if (isYesterday(dateInMilli * 1000)) {
            return resources.getString(R.string.yesterday);
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", getLocale(resources));
            Date date = new Date(dateInMilli * 1000);

            return simpleDateFormat.format(date);
        }
    }

    private static boolean isToday(long dateInMilli) {
        return DateUtils.isToday(dateInMilli);
    }

    private static boolean isTomorrow(long dateInMilli) {
        return DateUtils.isToday(dateInMilli -
                DateUtils.DAY_IN_MILLIS);
    }

    private static boolean isYesterday(long dateInMilli) {
        return DateUtils.isToday(dateInMilli +
                DateUtils.DAY_IN_MILLIS);
    }

    public static String getHour(Resources resources, long dateInMilli, String zoneId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", getLocale(resources));
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);

        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar1 = Calendar.getInstance();
        int now = calendar1.get(Calendar.HOUR_OF_DAY);
        calendar1.setTimeInMillis(dateInMilli * 1000);
        int hour = calendar1.get(Calendar.HOUR_OF_DAY);


        return now == hour ? resources.getString(R.string.now) : simpleDateFormat.format(calendar1.getTime());


    }

    public static Locale getLocale(Resources r) {
        boolean isArabic = r.getBoolean(R.bool.support_rtl);

        if (isArabic)
            return new Locale("ar");
        else return Locale.ENGLISH;
    }
}
