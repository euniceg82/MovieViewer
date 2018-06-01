package com.exam.movieviewer.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eunice Galang on 5/30/2018.
 */

public class Utility {

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String convertMinHour(String minutes){
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("mm");

        try {
            Date dt = sdf.parse(minutes);
            sdf = new SimpleDateFormat("HH:mm");
            time = sdf.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convertDate(String dateToFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dateToFormat);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("MMM dd, yyyy");
        String finalDate = timeFormat.format(myDate);

        return finalDate;
    }

    public static String convertReadableTime(String convertTime){
        String time = null;

        String timeFormat = convertMinHour(convertTime);
        try {
            String[] split = timeFormat.split(":");
            if(split.length == 2) {
                String hour = split[0];
                String min = split[1];
                if (hour.length() == 2) {
                    if (hour.startsWith("0")) hour = hour.replace("0", "");
                }
                time = hour + "hr ";
                if (!min.equals("00")) time = time + min + "mins";
            }
        }catch (Exception ex){
            time = convertTime;
        }

        return time;
    }
}
