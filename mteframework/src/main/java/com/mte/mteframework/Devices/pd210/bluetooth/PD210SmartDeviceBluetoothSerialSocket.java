package com.mte.mteframework.Devices.pd210.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mte.mteframework.security.ApplicationPermissions;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.Executors;

public class PD210SmartDeviceBluetoothSerialSocket implements Runnable
{

    private static final UUID BLUETOOTH_SPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BroadcastReceiver disconnectBroadcastReceiver;

    private final Context context;
    private com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceBluetoothSerialListener listener;
    private final BluetoothDevice device;
    private BluetoothSocket socket;
    private boolean connected;
    private int ResponseExpectedBytesLen=0;
    private int ExpectedBytesActuallyRead = 0;
    private boolean WaitForExpected = false;
    private boolean ReadEnable = false;

    //#############################################################################################################
    //#############################################################################################################
    public PD210SmartDeviceBluetoothSerialSocket(Context context, BluetoothDevice device) {
        if(context instanceof Activity)
            throw new InvalidParameterException("expected non UI context");
        this.context = context;
        this.device = device;
        disconnectBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(listener != null)
                    listener.onSerialIoError(new IOException("background disconnect"));
                disconnect(); // disconnect now, else would be queued until UI re-attached
            }
        };
    }
    //#############################################################################################################
    //#############################################################################################################
    String getName()
    {


        if(ContextCompat.checkSelfPermission(this.context,Manifest.permission.BLUETOOTH ) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    return device.getName() != null ? device.getName() : device.getAddress();
                }
            }
        }


            return "Permission Not Granted";


    }
    //#############################################################################################################
    //#############################################################################################################
    public boolean checkforpermission()
    {
        try
        {
            boolean response = false;

            if(ContextCompat.checkSelfPermission(this.context,Manifest.permission.BLUETOOTH ) == PackageManager.PERMISSION_GRANTED) {

                if(ContextCompat.checkSelfPermission(this.context,Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this.context,Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                        response = true;
                    }
                }
            }
            return  response;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    //#############################################################################################################
    //#############################################################################################################

    /**
     * connect-success and most connect-errors are returned asynchronously to listener
     */
    void connect(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceBluetoothSerialListener listener) throws IOException {
        this.listener = listener;
        context.registerReceiver(disconnectBroadcastReceiver, new IntentFilter(com.mte.mteframework.Devices.pd210.bluetooth.PD210BluetoothConstants.INTENT_ACTION_DISCONNECT));
        Executors.newSingleThreadExecutor().submit(this);
    }

    //#############################################################################################################
    //#############################################################################################################

    void disconnect() {
        listener = null; // ignore remaining data and errors
        // connected = false; // run loop will reset connected
        if(socket != null) {
            try {
                socket.close();
            } catch (Exception ignored) {
            }
            socket = null;
        }
        try {
            context.unregisterReceiver(disconnectBroadcastReceiver);
        } catch (Exception ignored) {
        }
    }
    //================================================================================
    //================================================================================
    void write(byte[] data) throws IOException {
        if (!connected)
            throw new IOException("not connected");
        WaitForExpected = false;
        ReadEnable = true;
        socket.getOutputStream().write(data);
    }
    //================================================================================
    //================================================================================
    void write(byte[] data, int len) throws IOException
    {
        if (!connected)
            throw new IOException("not connected");
        //socket.getOutputStream().write(data);
        WaitForExpected = false;
        ReadEnable = true;
        socket.getOutputStream().write(data,0,len);
    }
    //================================================================================
    //================================================================================
    void write(byte[] data, int len, int responseexpectedlen) throws IOException
    {
        if (!connected)
            throw new IOException("not connected");
        //socket.getOutputStream().write(data);
        ResponseExpectedBytesLen = responseexpectedlen;
        ExpectedBytesActuallyRead = 0;
        WaitForExpected = true;
        ReadEnable = true;
        socket.getOutputStream().write(data,0,len);
    }
    //#############################################################################################################
    //#############################################################################################################
    @Override
    public void run()
    {

        try
        {
            if(ContextCompat.checkSelfPermission(this.context,Manifest.permission.BLUETOOTH ) == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        //===============================================================================================


                        try {

                            socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_SPP);
                            socket.connect();
                            if(listener != null)
                                listener.onSerialConnect();




                        } catch (Exception e) {
                            if(listener != null)
                                listener.onSerialConnectError(e);
                            try {
                                socket.close();
                            } catch (Exception ignored) {
                            }
                            socket = null;
                            return;
                        }
                        connected = true;
                        try {
                            byte[] buffer = new byte[1024];
                            int len;
                            //noinspection InfiniteLoopStatement
                            while (true)
                            {
                                if(ReadEnable)
                                {
                                    if(WaitForExpected)
                                    {
                                        len = socket.getInputStream().read(buffer,ExpectedBytesActuallyRead,ResponseExpectedBytesLen);
                                        if(len == ResponseExpectedBytesLen)
                                        {
                                            // MTEDebugLogger.Log(true,"MTE-SRV:","Complete packet");
                                            //it read everything
                                            byte[] data = Arrays.copyOf(buffer, ExpectedBytesActuallyRead+len);
                                            if(listener != null)
                                                listener.onSerialRead(data);
                                            ReadEnable =false;
                                        }
                                        else
                                        {//MTEDebugLogger.Log(true,"MTE-SRV:","Incomplete packet " + len);
                                            ExpectedBytesActuallyRead = len;
                                            ResponseExpectedBytesLen-= len;
                                        }
                                    }
                                    else {
                                        //normal read
                                        len = socket.getInputStream().read(buffer);
                                        byte[] data = Arrays.copyOf(buffer, len);
                                        if(listener != null)
                                            listener.onSerialRead(data);
                                    }


                                }
                                //len = socket.getInputStream().read(buffer);



                            }
                        } catch (Exception e) {
                            connected = false;
                            if (listener != null)
                                listener.onSerialIoError(e);
                            try {
                                socket.close();
                            } catch (Exception ignored) {
                            }
                            socket = null;
                        }

                        //===============================================================================================

                    }
                }
            }

        }
        catch(Exception ex)
        {

        }

    }
    //================================================================================
    //================================================================================

    //#############################################################################################################
    //#############################################################################################################



    //#############################################################################################################
    //#############################################################################################################


    //#############################################################################################################
    //#############################################################################################################

}
