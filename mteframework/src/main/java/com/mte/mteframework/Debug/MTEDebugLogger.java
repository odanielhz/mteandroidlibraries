package com.mte.mteframework.Debug;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    //=================================================================================
    public static void Toast(Context mcontext, String message)
    {
        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
    }

    //=================================================================================
    //=================================================================================



}
