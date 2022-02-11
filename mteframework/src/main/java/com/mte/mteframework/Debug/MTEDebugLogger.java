package com.mte.mteframework.Debug;


import android.util.Log;

public class MTEDebugLogger
{
    //=================================================================================
    //=================================================================================
    public  static void Log(String tag, String message)
    {
        try
        {

            Log.i(tag,message);
        }
        catch(Exception ex)
        {
            Log.i("CustomAppLog", ex.toString());
        }
    }
    //=================================================================================
    //=================================================================================
    public  static void Log(boolean tagenable, String tag, String message)
    {
        try
        {
            if(tagenable) {
                Log.i(tag, message);
            }
        }
        catch(Exception ex)
        {
            Log.i("CustomAppLog", ex.toString());
        }
    }
    //=================================================================================
    //=================================================================================
    public  static void i(String tag, String message)
    {
        try
        {

            Log.i(tag,message);
        }
        catch(Exception ex)
        {
            Log.i("CustomAppLog", ex.toString());
        }
    }
    //=================================================================================
    //=================================================================================
    public  static void i(boolean tagenable, String tag, String message)
    {
        try
        {
            if(tagenable) {
                Log.i(tag, message);
            }
        }
        catch(Exception ex)
        {
            Log.i("CustomAppLog", ex.toString());
        }
    }
    //=================================================================================
}
