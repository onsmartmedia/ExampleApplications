package com.videri.core.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yiminglin on 5/19/16.
 */
public class TimeUtil {

    private static final String TAG = "TimeUtil";
    //Date format: "yyyy-MM-dd'T'HH:mm:ss"
    //Date format: "yyyy-MM-dd HH:mm:ss"


    public static Date getCurrentTimeDate(String format){
        Date currentDateTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            if(CoreConstants.DEBUG)
             Log.d(TAG, "getCurrentTimeDate: " + sdf.format(currentDateTime));
            return sdf.parse(sdf.format(currentDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentTime(String format){

        return  getCurrentTimeDate(format).toString();
    }


    public static long getTimeDifference(Date date,String format) {

        return getTimeDifference(date,new Date(), format);
    }

    public static long getTimeDifference(Date date1, Date date2, String format){
        //TODO ??
        return date2.getTime() - date1.getTime();

    }

    public static long getTimeDifference(Date date1, Date date2){
        return getTimeDifference(date1, date2, null);
    }

    public static long getTimeDifference(String date1, String date2, String dateFormat) {
        long difference = -1;
        Date date1Time;
        Date date2Time;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        try {

            date1Time = sdf.parse(date1);
            date2Time = sdf.parse(date2);
            difference =  getTimeDifference(date2Time, date1Time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return difference;
    }



}
