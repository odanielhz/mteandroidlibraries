package com.mte.mteframework.views.recyclerview.bluetoothdevices;

import android.bluetooth.BluetoothDevice;

public class MTEBluetoothDeviceItem
{
    private String Name;
    private String Address;
    private int Image;

    private BluetoothDevice Device;

    //==============================================================================================================
    //==============================================================================================================
    public MTEBluetoothDeviceItem() {
    }
    //==============================================================================================================
    public MTEBluetoothDeviceItem(String name, String address, int image) {
        Name = name;
        Address = address;
        Image = image;
    }
    //##############################################################################################################
    //##############################################################################################################
    public MTEBluetoothDeviceItem(String name, String address, int image, BluetoothDevice device) {
        Name = name;
        Address = address;
        Image = image;
        Device = device;
    }


    //##############################################################################################################
    //##############################################################################################################
    public String getName() {
        return Name;
    }
    //##############################################################################################################
    //##############################################################################################################
    public void setName(String name) {
        Name = name;
    }
    //##############################################################################################################
    public String getAddress() {
        return Address;
    }
    //##############################################################################################################
    public void setAddress(String address) {
        Address = address;
    }
    //##############################################################################################################
    public int getImage() {
        return Image;
    }
    //##############################################################################################################
    public void setImage(int image) {
        Image = image;
    }
    //##############################################################################################################
    public BluetoothDevice getDevice() {
        return Device;
    }

    public void setDevice(BluetoothDevice device) {
        Device = device;
    }
}
