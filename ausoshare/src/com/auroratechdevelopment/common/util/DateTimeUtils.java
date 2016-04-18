package com.auroratechdevelopment.common.util;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Edward on 2015-09-13.
 */
public class DateTimeUtils {

    public static String getTimeSpanString(String UTCDateTime, String datePattern){

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        dateFormat.setTimeZone(TimeZone.getTimeZone(Locale.getDefault().toString()));

        try {
            final Date convertedDate = dateFormat.parse(UTCDateTime.replace("Z", ""));
            return DateUtils.getRelativeTimeSpanString(convertedDate.getTime()).toString();

        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return  "";
    }

    /*
        "7/22/2014 7:01:30 PM",
        public static final String datePattern = "MM/dd/yyyy hh:mm:ss aa";
     */
    public static Date getDateTime(String dateTimeString, String datePattern){

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        dateFormat.setTimeZone(TimeZone.getTimeZone(Locale.getDefault().toString()));

        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(dateTimeString);

        }catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

    /*  sample datePatten "dd-MM-yyyy" */
    public static String getDateString(Date date, String datePattern){

        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

    public static String getRecentDateTime(Date date){

        Calendar ci = Calendar.getInstance();

        long diffInMillisec = ci.getTimeInMillis() - date.getTime();
        long diff = TimeUnit.MILLISECONDS.toSeconds(diffInMillisec);

        diff /= 60; //minutes
        diff /= 60; //hours
        diff /= 24; //days

        //
        String format = (diffInMillisec < 0 || diff >0)? "E MM/dd h:mm a" : "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }
}
