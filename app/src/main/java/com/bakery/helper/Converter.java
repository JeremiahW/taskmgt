package com.bakery.helper;

import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangj on 10/22/15.
 */
public class Converter {
    public static String TAG = "Converter";
    public static String FormatStringDate(String date)
    {
        if(date.isEmpty()) return "";
        String returnDate = "";
        Log.d(TAG, "Date String: " + date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date curDate = null;
        try {
            curDate = formatter.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            returnDate = outputFormat.format(curDate);
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage());
        }
        // String dateString = formatter.format(curDate);
        return returnDate;
    }

}
