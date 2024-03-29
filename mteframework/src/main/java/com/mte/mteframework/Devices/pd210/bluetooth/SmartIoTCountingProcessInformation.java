package com.mte.mteframework.Devices.pd210.bluetooth;

import com.mte.mteframework.Debug.MTEDebugLogger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SmartIoTCountingProcessInformation
{
    public long TicketId;
    public float GrossWeight;
    public float NetWeight;
    public float TareWeight;
    public float UnitWeight;
    public long Pieces;
    public long SampleQty;
    public short PartId;
    public short TareId;
    public byte[] TareCode = new byte[14];
    public byte[] PartCode= new byte[14];
    public int Decimals;
    public int Platform;
    public byte[] DisplayStr = new byte[8];

    public int DisplayUnits;

    //#############################################################################################################
    public SmartIoTCountingProcessInformation()
    {}

    //#############################################################################################################
    //#############################################################################################################

    //#############################################################################################################
    //#############################################################################################################
    public void setByteArray(byte[] data)
    {
        try
        {
            ByteBuffer bdata;
            bdata = ByteBuffer.wrap(data);
            bdata.order(ByteOrder.LITTLE_ENDIAN);

            TicketId = bdata.getInt(0);
            GrossWeight = bdata.getFloat(4);
            NetWeight  = bdata.getFloat(8);
            TareWeight = bdata.getFloat(12);
            UnitWeight = bdata.getFloat(16);
            Pieces = bdata.getInt(20);
            SampleQty = bdata.getInt(24);
            PartId = bdata.getShort(28);
            TareId = bdata.getShort(30);
            System.arraycopy(data,32,TareCode,0,14);
            //PartCode = bdata.getFloat(4);
            System.arraycopy(data,46,PartCode,0,14);
            Decimals =  (int)(((byte)(bdata.get(60)))&0xff);
            Platform = (int)(((byte)(bdata.get(61)))&0xff);
            System.arraycopy(data,62,DisplayStr,0,8);
            DisplayUnits = (int)(((byte)(bdata.get(70)))&0xff);


        }
        catch(Exception ex)
        {
            MTEDebugLogger.Log(true, "MTE-", ex.toString());
        }
    }

    //#############################################################################################################
    //#############################################################################################################
    public String getDisplayUnits()
    {
               switch (DisplayUnits)
               {
                   default:
                   case 0: return "kg.";
                   case 1: return "gr.";
                   case 2: return "lb.";
                   case 3: return "Pz.";
               }

    }
    public String getDisplayUnits(int units)
    {
        switch (units)
        {
            default:
            case 0: return "kg.";
            case 1: return "gr.";
            case 2: return "lb.";
            case 3: return "Pz.";
        }
    }
    //#############################################################################################################
    //#############################################################################################################


    //#############################################################################################################
    //#############################################################################################################

}
