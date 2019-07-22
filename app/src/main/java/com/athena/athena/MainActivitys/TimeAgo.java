package com.athena.athena.MainActivitys;


import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeAgo {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String calculateTime(long time) {

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
            System.out.println("The Time is "+time);
        }

        long now = getUTCTime();
        final long diff =now-time;
        System.out.println("Minute Millus is "+MINUTE_MILLIS+" The time is "+time +" The diff is"+diff);
        if (diff <MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "1 min";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " mins";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days";
        }
    }
    public static long getUTCTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long utcTime = cal.getTimeInMillis();
        return utcTime;
    }

}
