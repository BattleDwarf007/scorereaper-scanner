package com.battledwarf.scorereaper.util;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class StringUtils {

    private static final DecimalFormat df = new DecimalFormat("00");

    public static boolean isEmpty(String str) {
        return str != null && str.trim().length() != 0;
    }

    public static String getFormattedTime(long t) {
        int minutes = (int) (t / (1000 * 60));
        int seconds = (int) (t / 1000 % 60);
        int millis = (int) (t % 1000);
        return df.format(minutes) + ":" + df.format(seconds) + "." + df.format(millis);

    }

    public static String getStopWatchFormattedTime(long t) {
        int minutes = (int) (t / (1000 * 60));
        int seconds = (int) (t / 1000 % 60);
        return df.format(minutes) + ":" + df.format(seconds);

    }

    // get current timestamp
    public static String getDateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date objDate = null;
        try {
            objDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        assert objDate != null;
        return dateFormat2.format(objDate);
    }

}
