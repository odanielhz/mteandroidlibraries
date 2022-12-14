package com.mte.mteframework.Devices.pd210.bluetooth;

import android.content.Context;
import android.content.SharedPreferences;

public class PD210BluetoothSettings
{
    public final String PREFS_DEVICE_INFO = "PD210BluetoothDeviceSettings";
    public final String SETTINGS_KEY_TOKEN = "Token";
    public final int SETTINGS_KEY_TOKEN_VAL = 231047;
    public final String SETTINGS_KEY_DEVICENAME = "DeviceName";
    public final String SETTINGS_KEY_DEVICEADDRESS = "DeviceAddress";
    public final String SETTINGS_KEY_DEVICEIMAGERESOURCE = "DeviceImage";


    private String DeviceName="";
    private String DeviceAddress="";
    private int DeviceImage = -1;
    private boolean DeviceEnabled = false;

   /* final String DEVNAME_KEY = "DeviceName";
    final String DEVADDRESS_KEY = "DeviceAddress";
    final String DEVIMAGE_KEY = "DeviceImage";
    */

    public PD210BluetoothSettings()
    {

    }
    //======================================================================================================
    //======================================================================================================
    public PD210BluetoothSettings(String devname, String devaddress, int devimg)
    {
        DeviceName = devname;
        DeviceAddress = devaddress;
        DeviceImage = devimg;
    }
    //======================================================================================================
    //======================================================================================================

    public void LoadSettings(Context context)
    {
        SharedPreferences devsetttings = context.getSharedPreferences(PREFS_DEVICE_INFO,0);

        if(devsetttings.getInt(SETTINGS_KEY_TOKEN,0) == SETTINGS_KEY_TOKEN_VAL)
        {
            DeviceName = devsetttings.getString(SETTINGS_KEY_DEVICENAME,"UNDEFINED");
            DeviceAddress = devsetttings.getString(SETTINGS_KEY_DEVICEADDRESS,"00:00:00:00");
            DeviceImage = devsetttings.getInt(SETTINGS_KEY_DEVICEIMAGERESOURCE,0);
        }
        else
        {
            DeviceName ="No Name";
            DeviceAddress = "00:00:00:00";
            DeviceImage = -1;
        }


    }
    //======================================================================================================
    //======================================================================================================

    public void SaveSettings(Context context)
    {
        SharedPreferences devsetttings = context.getSharedPreferences(PREFS_DEVICE_INFO,0);
        SharedPreferences.Editor editor = devsetttings.edit();

        //Set data
        editor.putInt(SETTINGS_KEY_TOKEN, 231047);
        editor.putString(SETTINGS_KEY_DEVICENAME, DeviceName);
        editor.putString(SETTINGS_KEY_DEVICEADDRESS, DeviceAddress);
        editor.putInt(SETTINGS_KEY_DEVICEIMAGERESOURCE, DeviceImage);

        editor.apply();

    }
    //======================================================================================================
    //======================================================================================================

    public String getDeviceName() {
        return DeviceName;
    }

    public String getDeviceAddress() {
        return DeviceAddress;
    }

    public int getDeviceImage() {
        return DeviceImage;
    }




    //======================================================================================================
    //======================================================================================================

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public void setDeviceAddress(String deviceAddress) {
        DeviceAddress = deviceAddress;
    }

    public void setDeviceImage(int deviceImage) {
        DeviceImage = deviceImage;
    }

    public boolean isDeviceEnabled() {
        return DeviceEnabled;
    }

    public void setDeviceEnabled(boolean deviceEnabled) {
        DeviceEnabled = deviceEnabled;
    }


    //======================================================================================================
    //======================================================================================================


}
