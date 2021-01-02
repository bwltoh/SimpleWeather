package com.example.simpleweather.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static String getDate(long dateInMilli){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Date date =new Date();
        date.setTime(dateInMilli*1000);
       return simpleDateFormat.format(date);
    }

    public static String getDateWithDayName(long dateInMilli){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE.MM.yyyy",Locale.getDefault());
        Date date =new Date(dateInMilli*1000);

        return simpleDateFormat.format(date);
    }

    public static String getDayName(long dateInMilli){
        String dayName;
        if (isToday(dateInMilli*1000)){
            return "Today";
        }else if (isTomorrow(dateInMilli*1000)){
            return "Tomorrow";
        }else if (isYesterday(dateInMilli*1000)){
            return "Yesterday";
        }else {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE", Locale.getDefault());
            Date date=new Date(dateInMilli*1000);

            return simpleDateFormat.format(date);
        }
    }

    private static boolean isToday(long dateInMilli){
      return   DateUtils.isToday(dateInMilli);
    }

    private static boolean isTomorrow(long dateInMilli){
        return DateUtils.isToday(dateInMilli-
                DateUtils.DAY_IN_MILLIS);
    }
    private static boolean isYesterday(long dateInMilli){
        return DateUtils.isToday(dateInMilli+
                DateUtils.DAY_IN_MILLIS);
    }

    public static String getHour(long dateInMilli,String zoneId){

        TimeZone timeZone=TimeZone.getTimeZone(zoneId);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
         simpleDateFormat.setTimeZone(timeZone);
        Date date=new Date();
        date.setTime(dateInMilli*1000);
        return simpleDateFormat.format(date);
    }



}
