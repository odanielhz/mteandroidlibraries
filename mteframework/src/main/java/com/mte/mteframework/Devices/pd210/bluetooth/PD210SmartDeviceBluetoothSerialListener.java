package com.mte.mteframework.Devices.pd210.bluetooth;

public interface PD210SmartDeviceBluetoothSerialListener
{

        void onSerialConnect      ();
        void onSerialConnectError (Exception e);
        void onSerialRead         (byte[] data);
        void onSerialIoError      (Exception e);
        void onSerialDisconnect   ();




}
