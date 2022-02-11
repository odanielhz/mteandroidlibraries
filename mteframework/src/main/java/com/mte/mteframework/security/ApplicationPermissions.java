package com.mte.mteframework.security;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mte.mteframework.Debug.MTEDebugLogger;

public class ApplicationPermissions
{

    public static final int APPLICATION_PERMISSION_CODE_BLUETOOTH = 3000;

    public ApplicationPermissions()
    {}

    //######################################################################################################################3
    //######################################################################################################################3
    public static boolean checkForPermission(Context context, String permission)
    {
        try
        {
            boolean response = false;
            if (ContextCompat.checkSelfPermission(context,permission ) != PackageManager.PERMISSION_GRANTED )
            {
                response = false;
            }
            else
            {
                response = true;
            }

            return response;
        }
        catch(Exception ex)
        {
            //MTEDebugLogger.Log(true,"MTE-SECURITY","Exception on checkForPermission()");
            return false;
        }
    }
    //######################################################################################################################3
    //######################################################################################################################3
    public static boolean checkForPermission(Context context, String permission, int requestcode)
    {
        try
        {
            boolean response = false;
            if (ContextCompat.checkSelfPermission(context,permission ) != PackageManager.PERMISSION_GRANTED )
            {
                response = false;
            }
            else
            {
                response = true;
            }

            return response;
        }
        catch(Exception ex)
        {
            //MTEDebugLogger.Log(true,"MTE-SECURITY","Exception on checkForPermission()");
            return false;
        }
    }
    //######################################################################################################################3
    //######################################################################################################################3
    public static void onRequestPermissionResults(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == APPLICATION_PERMISSION_CODE_BLUETOOTH)
        {
            //bluetooth result
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(context, "Bluetooth Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //######################################################################################################################3
    //######################################################################################################################3




    //######################################################################################################################3
    //######################################################################################################################3




    //######################################################################################################################3
    //######################################################################################################################3



}
