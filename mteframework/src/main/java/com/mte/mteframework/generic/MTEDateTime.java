package com.mte.mteframework.generic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MTEDateTime
{
    //========================================================================================
    //========================================================================================
    public static Calendar GetDate(String datestr, String format)
    {
        try {

            SimpleDateFormat frmt = new SimpleDateFormat(format, Locale.getDefault());
            Date dt;
            GregorianCalendar cal = new GregorianCalendar();

            dt = frmt.parse(datestr);
            if(dt!=null)
                cal.setTime(dt);

            return cal;
        }catch (Exception ex)
        {
            return new GregorianCalendar(1982,2,2);
        }
    }
    //========================================================================================
    //========================================================================================
    public static String getString(Calendar cal, String format)
    {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

            return df.format(cal.getTime());
        }
        catch (Exception ex)
        {
            return "1982-02-02 10:30:00";
        }
    }



    //========================================================================================
    //========================================================================================



    //========================================================================================
    //========================================================================================
}
