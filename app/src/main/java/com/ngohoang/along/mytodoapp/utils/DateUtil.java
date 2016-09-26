package com.ngohoang.along.mytodoapp.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 26/09/2016.
 */

public class DateUtil {
    public static  long longValueFromDateTime(String dateTime){
        long date = 0;
        try {
            date = (new SimpleDateFormat("dd/MM/yyyy HH:ss")).parse(dateTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static  String stringDateValueFromLong(long dateTime){
        String date = "";

            date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(dateTime));

        return date;
    }
    public static  String stringTimeValueFromLong(long dateTime){
        String date = "";

        date = new SimpleDateFormat("HH:ss").format(new Date(dateTime));

        return date;
    }
    public static  String stringDateTimeValueFromLong(long dateTime){
        String date = "";

        date = new SimpleDateFormat("dd/MM/yyyy HH:ss").format(new Date(dateTime));

        return date;
    }
}
