package com.mte.mteframework.Devices.pd210.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.mte.mteframework.Debug.MTEDebugLogger;
import com.mte.mteframework.Devices.pd210.bluetooth.MTEBluetoothCommandHandler;
import com.mte.mteframework.Devices.pd210.PD210PacketHandler;
import com.mte.mteframework.generic.MTETimeoutHandler;

import java.util.Arrays;

public class PD210SmartDeviceBluetooth implements ServiceConnection, PD210SmartDeviceBluetoothSerialListener, MTETimeoutHandler.MTETimeoutHandlerListener
{

    private static final String TAG ="MTE-PD210DEV-";
    private static final boolean TAGENABLE = true;

    public String DeviceName;
    public String DeviceAddress;
    public int DeviceImg;



    private enum Connected { False, Pending, True }



    private Connected connected = Connected.False;

    public Context mContext;
    public boolean serviceInitialStart = true;
    //##############################################################
    private PD210SmartDeviceBluetoothSerialService service;
    private PD210SmartDeviceBluetoothListener listener;

    //##############################################################
    //byte[] PacketTx =  new byte[120];
    //byte[] PacketRx =  new byte[120];
    //byte[] datareceived;

    int RxState = 0;
    int RxIndex =0;
    int ArgsLen=0;
    int Index = 0;
    int CRC = 0;
    int CommandTx = 0;
    int Response = 0;
    int CommandRx = 0;
    int CurrentTokenId = 0;
    boolean WaitingResponse = false;
    boolean DeviceBusy = false;
    MTETimeoutHandler timeoutHandler =  new MTETimeoutHandler();
    public int MonitorStep = 0;
    public int AlternateCommand=0;
    public boolean AlternateCommandBusy =false;

    PD210PacketHandler PD210PacketRx;
    PD210PacketHandler PD210PacketTx;
    MTEBluetoothCommandHandler CurrentCommandHandler= new MTEBluetoothCommandHandler();
    //##############################################################



    //************************************************************************************************************
    //************************************************************************************************************
    public interface PD210SmartDeviceBluetoothListener
    {
        void onDeviceConnect();
        void onDeviceDisconnect();
        void onDeviceTimedOut();
        void onDataReceived(int tokenId, byte[] args, int len);
        void onDataSentException(int tokenId, String err);
        void onDataReceiveException(int tokenId, String err);
    }

    //************************************************************************************************************
    //************************************************************************************************************
    private void SetLog(String str)
    {
        try {
            MTEDebugLogger.Log(TAGENABLE,TAG,str);
        }
        catch (Exception ex)
        {
            MTEDebugLogger.Log(TAGENABLE,TAG, "Exception on SetLog. " + ex.toString());
        }
    }
    //****************************************************************************
    //****************************************************************************
    //Constructor
    public PD210SmartDeviceBluetooth()
    {
        timeoutHandler.setListener(this);

        PD210PacketTx =  new PD210PacketHandler();
        PD210PacketTx.setArgsLen(120);
        PD210PacketRx =  new PD210PacketHandler();
        PD210PacketRx.setArgsLen(120);

    }
    //****************************************************************************
    //Constructor
    public PD210SmartDeviceBluetooth(String devName, String deviceAddress, int deviceImg)
    {
        DeviceName = devName;
        DeviceAddress = deviceAddress;
        DeviceImg = deviceImg;

        timeoutHandler.setListener(this);
    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder)
    {
        try
        {
            SetLog("onServiceConnected");
            service = ((PD210SmartDeviceBluetoothSerialService.SerialBinder) binder).getService();
            service.attach(this);
            if(serviceInitialStart)
            {
                serviceInitialStart = false;

                ((Activity)mContext).runOnUiThread(this::connect);

            }
        }
        catch(Exception ex)
        {
            SetLog("onServiceConnected Exception. " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        try
        {
            SetLog("onServiceDisconnected ");
            service = null;
        }
        catch (Exception ex)
        {
            SetLog("onServiceDisconnected Exception " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    @Override
    public void onSerialConnect() {

        SetLog("device connected!! ");
        connected = Connected.True;

        if(listener!=null)
        {
            listener.onDeviceConnect();
        }

    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onSerialConnectError(Exception e) {
        disconnect();

    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onSerialRead(byte[] data) {
        receive(data);

    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onSerialIoError(Exception e) {

        disconnect();

    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onSerialDisconnect() {
        if(listener!=null)
        {
            listener.onDeviceDisconnect();
        }
    }

    //****************************************************************************
    //****************************************************************************
    public Context getmContext() {
        return mContext;
    }
    //****************************************************************************
    //****************************************************************************
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
    //****************************************************************************
    //****************************************************************************

    public boolean isConnected() {
        if(connected == Connected.True){return true;}

        return false;
    }

    //****************************************************************************
    //****************************************************************************
    //API
    public void disconnect()
    {
        try
        {
            connected = Connected.False;
            service.disconnect();
        }
        catch(Exception ex)
        {
            SetLog("disconnect Exception: " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    public void onStart(Context context)
    {
        try
        {
            mContext =context;
            if(service != null)
            {
                SetLog("attaching service");
                service.attach(this);
            }
            else
            {
                SetLog("starting service");
                mContext.startService(new Intent(mContext, PD210SmartDeviceBluetoothSerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change

            }
        }
        catch(Exception ex) {
            SetLog("onStart Exception: " + ex.toString());
        }
    }
    //****************************************************************************
    //****************************************************************************
    public void onDestroy(Context context)
    {
        try
        {
            if(connected != Connected.False)
            {
                SetLog("destroy() disconnect service");
                disconnect();
            }

            //Stop service
            SetLog("stoping service");
            context.stopService(new Intent(context, PD210SmartDeviceBluetoothSerialService.class));
        }
        catch(Exception ex)
        {
            SetLog("onDestroy Exception: " + ex.toString());
        }
    }

    //****************************************************************************
    //****************************************************************************
    public void onStop(Context context)
    {
        try
        {
            if(service != null && ! ((Activity)context).isChangingConfigurations())
            {
                SetLog("detaching service");
                service.detach();
            }
        }
        catch (Exception ex)
        {
            SetLog("onStop Exception: " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    public void onResume(Context context)
    {
        try
        {
          if(service!=null)
          {
              SetLog("on Resume ..connecting service");
              ((Activity)context).runOnUiThread(this::connect);
          }
          else {
              SetLog("on Resume ..service is null");
          }
        }catch(Exception ex)
        {
            SetLog("onResume Exception: " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    public void onAttach(Context context)
    {
        try
        {
            SetLog("onAttach binding service");
            context.bindService(new Intent(context, PD210SmartDeviceBluetoothSerialService.class), this, Context.BIND_AUTO_CREATE);
        }
        catch (Exception ex)
        {
            SetLog("onAttach Exception: " + ex.toString());
        }

    }
    //****************************************************************************
    //****************************************************************************
    public void onDetach(Context context)
    {
        try
        {
            SetLog("onDetach unbinding service");
            context.unbindService(this);
        }
        catch (Exception ex)
        {
            SetLog("onDetach Exception: " + ex.toString());
        }

    }

    //****************************************************************************
    //****************************************************************************
    public void connect(Context context)
    {
        try
        {

            SetLog("connect(context) ");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(DeviceAddress);
            //status("connecting...");
            //status("Connecting...", R.color.yellow_warning, R.color.dark_gray_1);
            connected = Connected.Pending;

            PD210SmartDeviceBluetoothSerialSocket socket = new PD210SmartDeviceBluetoothSerialSocket(context.getApplicationContext(), device);
            service.connect(socket);

        }
        catch (Exception ex)
        {
            SetLog("connect Exception: " + ex.toString());
        }
    }
    //****************************************************************************
    //****************************************************************************
    public void connect()
    {
        try
        {
            SetLog("connect() ");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(DeviceAddress);
            //status("connecting...");
            //status("Connecting...", R.color.yellow_warning, R.color.dark_gray_1);
            connected = Connected.Pending;

            PD210SmartDeviceBluetoothSerialSocket socket = new PD210SmartDeviceBluetoothSerialSocket(mContext.getApplicationContext(), device);
            service.connect(socket);

        }
        catch (Exception ex)
        {
            SetLog("connect Exception: " + ex.toString());
        }
    }

    //****************************************************************************
    //****************************************************************************
    private void send(byte[] data, int len)
    {
        try
        {

        }
        catch(Exception ex)
        {

        }

    }
    //****************************************************************************
    //****************************************************************************
    private void receive(byte[] data)
    {
        try
        {


            for(int x=0;x<data.length;x++)
            {
                //==============================================================================
                //==============================================================================
                switch(PD210PacketRx.getRxState())
                {
                    //==============================================================================
                    case 0:
                        if(data[x] == 0x3A)
                        {
                            PD210PacketRx.setRxState(1);
                        }
                        break;
                    //==============================================================================
                    case 1:
                        if(data[x] == 0x50)
                        {
                            PD210PacketRx.setRxState(2);
                        }
                        else
                        {
                            PD210PacketRx.setRxState(0);
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }
                        break;
                    //==============================================================================
                    case 2:
                        if(data[x] == 0x44)
                        {
                            PD210PacketRx.setRxState(3);
                        }
                        else
                        {
                            PD210PacketRx.setRxState(0);
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }
                        break;
                    //==============================================================================
                    case 3: //Command
                        if((int)data[x] == (int)PD210PacketTx.CommandExpected)
                        {
                            PD210PacketRx.setCommand(data[x]);
                            PD210PacketRx.setRxState(4);
                        }
                        else
                        {
                            PD210PacketRx.setRxState(0);
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }
                        break;
                    //==============================================================================
                    case 4:  //REsponse
                        PD210PacketRx.setRxState(5);
                        PD210PacketRx.Response = data[x];
                        break;
                    //==============================================================================
                    case 5:  //Index
                        PD210PacketRx.setRxState(6);
                        PD210PacketRx.Index = data[x]<<8;
                        break;
                    //==============================================================================
                    case 6:  //Index
                        PD210PacketRx.setRxState(7);
                        PD210PacketRx.Index |= data[x]&0xff;
                        break;
                    //==============================================================================
                    case 7:  //Args Len
                        if(data[x] == 0)
                        {
                            //No args
                            PD210PacketRx.setRxArgsLen((int) data[x]);// = (int)data[x];
                            PD210PacketRx.setRxState(9);
                            PD210PacketRx.setRxIndex(0);

                        }
                        else {
                            PD210PacketRx.setRxArgsLen((int) data[x]);// = (int)data[x];
                            PD210PacketRx.setRxState(8);
                            PD210PacketRx.setRxIndex(0);
                        }
                        break;
                    //==============================================================================
                    case 8:  //Data Receivded
                        PD210PacketRx.Args[PD210PacketRx.RxIndex] = data[x];
                        //dataRx[RxIndex] = data[x];
                        //PD210PacketRx.IncrementRxIndex();
                        PD210PacketRx.RxIndex++;
                        if(PD210PacketRx.RxIndex>=PD210PacketRx.RxArgsLen)
                        {
                            //Finished
                            PD210PacketRx.Args[PD210PacketRx.RxIndex] = 0;
                            PD210PacketRx.setRxState(9);
                        }
                        break;
                    //==============================================================================
                    case 9:  //CRC 0
                        PD210PacketRx.setRxState(10);
                        break;
                    //==============================================================================
                    case 10:  //CRC 1
                        PD210PacketRx.setRxState(11);
                        break;
                    //==============================================================================
                    case 11:  //End Char
                        if(data[x] == 0x3B)
                        {
                            PD210PacketRx.setRxState(12);
                        }
                        else
                        {
                            PD210PacketRx.setRxState(0);
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }
                        break;
                    //==============================================================================
                    case 12:  //0x0d
                        if(data[x] == 0x0d)
                        {
                            PD210PacketRx.setRxState(13);
                        }
                        else
                        {
                            PD210PacketRx.setRxState(0);
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }
                        break;
                    //==============================================================================
                    case 13:  //x0a
                        if(data[x] == 0x0a)
                        {
                            //Process Data Completes
                            //SetLog("Packet Received Ok");

                            //ProcessCommandResponse(PD210PacketRx);
                            //================================================================
                            //Set the datareceived event
                            if(listener!=null)
                            {
                                //Copy buffer
                                byte[] datarx = Arrays.copyOf(PD210PacketRx.Args, PD210PacketRx.RxArgsLen);
                                listener.onDataReceived(CurrentTokenId, datarx,PD210PacketRx.RxArgsLen);
                            }

                            timeoutHandler.stop();
                            DeviceBusy = false;
                            WaitingResponse = false;
                            //CurrentCommandHandler.setState(MTEBluetoothCommandHandler.ProcessState.Completed);
                        }

                        PD210PacketRx.setRxState(0);

                        break;
                }
                //==============================================================================
                //==============================================================================
            }



        }
        catch(Exception ex)
        {

        }
    }
    //****************************************************************************
    //****************************************************************************

    public void setListener(PD210SmartDeviceBluetoothListener listener)
    {
        this.listener = listener;
    }
    //****************************************************************************
    //****************************************************************************
    @Override
    public void onTimedOut()
    {
         //the last transaction has just timedout
        WaitingResponse = false;
        DeviceBusy = false;
        PD210PacketRx.RxState = 0;


         if(listener !=null)
         {listener.onDeviceTimedOut();}
    }
    //****************************************************************************
    //****************************************************************************
    // API
    int  setPacket(int command, int argsLen, int index, byte[] args)
    {

        PD210PacketTx.Command = command;
        PD210PacketTx.CommandExpected = command;
        PD210PacketTx.Args[0] = 0x3A;   //Starting char
        PD210PacketTx.Args[1] = 'P';
        PD210PacketTx.Args[2] = 'D';
        PD210PacketTx.Args[3] = (byte)(command & 0xff);   //Command
        PD210PacketTx.Args[4] = (byte)((index>>8) & 0xff);   //index high
        PD210PacketTx.Args[5] = (byte)((index) & 0xff);   //index low
        PD210PacketTx.Args[6] = (byte)(argsLen & 0xff);  //Args len

        if(args!=null)
        {
            for(int x=0;x<argsLen;x++)
            {
                PD210PacketTx.Args[7+x] = args[x];
            }
        }

        PD210PacketTx.Args[7+argsLen] = 0x00;   //CRC Hight
        PD210PacketTx.Args[8+argsLen] = 0x00;   //CRC Low
        PD210PacketTx.Args[9+argsLen] = 0x3B;   //End char
        PD210PacketTx.Args[10+argsLen] = 0x0d;  //
        PD210PacketTx.Args[11+argsLen] = 0x0a;

        return (12+argsLen);
    }
    //****************************************************************************
    //****************************************************************************
    public void getWeightString(int tokenId)
    {
        try {
            int packetlen = 0;
            if(!DeviceBusy)
            {
                CurrentTokenId = tokenId;
                //Set the packet to get th weight string
                packetlen = setPacket(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceConstants.IOT_COMMAND_GET_WEIGHT_STRING, 0, 0, null);
                service.write(PD210PacketTx.Args, packetlen);
                //SetLog("getWeightString  Packet Sent");
                //set timeout
                timeoutHandler.set(1000);
                //set flags
                WaitingResponse = true;
                DeviceBusy = true;
            }
            else
            {
                //SetLog("getWeightString  DeviceBusy");
            }
        }
        catch(Exception ex)
        {
            if(listener!=null)
            {
                listener.onDataSentException(tokenId, "getWeightString Exception." + ex.toString());
            }
        }
    }
    //****************************************************************************
    //****************************************************************************
    public void getDisplayString(int tokenId)
    {
        try {
            int packetlen = 0;
            if(!DeviceBusy)
            {
                CurrentTokenId = tokenId;
                //Set the packet to get th weight string
                packetlen = setPacket(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceConstants.IOT_COMMAND_GET_DISPLAY_STRING, 0, 0, null);
                service.write(PD210PacketTx.Args, packetlen);
                //SetLog("getWeightString  Packet Sent");
                //set timeout
                timeoutHandler.set(1000);
                //set flags
                WaitingResponse = true;
                DeviceBusy = true;
            }
            else
            {
                //SetLog("getWeightString  DeviceBusy");
            }
        }
        catch(Exception ex)
        {
            if(listener!=null)
            {
                listener.onDataSentException(tokenId, "getDisplayString Exception." + ex.toString());
            }
        }
    }
    //****************************************************************************
    //****************************************************************************
    public void getWeightStructure(int tokenId)
    {
        try {
            int packetlen = 0;
            if(!DeviceBusy)
            {
                CurrentTokenId = tokenId;
                //Set the packet to get th weight string
                packetlen = setPacket(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceConstants.IOT_COMMAND_GET_WEIGHT_STRUCTURED_DATA, 0, 0, null);
                service.write(PD210PacketTx.Args, packetlen);
                //SetLog("getWeightStructure  Packet Sent");
                //set timeout
                timeoutHandler.set(5000);
                //set flags
                WaitingResponse = true;
                DeviceBusy = true;
            }
            else
            {
                //SetLog("getWeightStructure  DeviceBusy");
            }
        }
        catch(Exception ex)
        {
            if(listener!=null)
            {
                listener.onDataSentException(tokenId, "getWeightString Exception." + ex.toString());
            }
        }
    }
    //****************************************************************************
    //****************************************************************************
    public void setKeypadKey(int tokenId, byte keypadval)
    {
        try {
            int packetlen = 0;
            if(!DeviceBusy)
            {
                CurrentTokenId = tokenId;
                //Set the packet to get th weight string
                packetlen = setPacket(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceConstants.IOT_COMMAND_SET_KEYBOARD_KEY, 1, 0, new byte[]{keypadval});
                service.write(PD210PacketTx.Args, packetlen);
                //SetLog("getWeightStructure  Packet Sent");
                //set timeout
                timeoutHandler.set(5000);
                //set flags
                WaitingResponse = true;
                DeviceBusy = true;
            }
            else
            {
                //SetLog("getWeightStructure  DeviceBusy");
            }
        }
        catch(Exception ex)
        {
            if(listener!=null)
            {
                listener.onDataSentException(tokenId, "setKeypadKey Exception." + ex.toString());
            }
        }
    }

    //****************************************************************************
    //****************************************************************************
//****************************************************************************
    //****************************************************************************
    public void printTicketCommand(int tokenId)
    {
        try {
            int packetlen = 0;
            if(!DeviceBusy)
            {
                CurrentTokenId = tokenId;
                //Set the packet to get th weight string
                packetlen = setPacket(com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceConstants.IOT_COMMAND_PRINT_TICKET_DATA, 0, 0, null);
                service.write(PD210PacketTx.Args, packetlen);
                //SetLog("getWeightStructure  Packet Sent");
                //set timeout
                timeoutHandler.set(5000);
                //set flags
                WaitingResponse = true;
                DeviceBusy = true;
            }
            else
            {
                //SetLog("getWeightStructure  DeviceBusy");
            }
        }
        catch(Exception ex)
        {
            if(listener!=null)
            {
                listener.onDataSentException(tokenId, "printTicketCommand Exception." + ex.toString());
            }
        }
    }

    //****************************************************************************
    //****************************************************************************


}
