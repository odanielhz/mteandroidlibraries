package com.mte.mteframework.Devices.pd210;

public class PD210PacketHandler
{
    public int RxState = 0;
    public byte[] Args;
    public int RxIndex=0;
    public int Command=0;
    public int CommandExpected =0;
    public int RxArgsLen=0;
    public int Response=0;
    public int Index=0;
    public int Counter=0;



    //================================================================================================
    //Constant
    public static final byte PD210_COMMAND_GET_NET_WEIGHT_STRING     =               0x01;








    //*****************************************************************************************************
    //*****************************************************************************************************
    public PD210PacketHandler()
    {}

    //*****************************************************************************************************
    //*****************************************************************************************************

    //***************************************************************************************
    public int getRxState() {
        return RxState;
    }
    //***************************************************************************************
    public void setRxState(int rxState) {
        RxState = rxState;
    }
    //***************************************************************************************
    public int getRxIndex() {
        return RxIndex;
    }
    //***************************************************************************************
    public void setRxIndex(int rxIndex) {
        RxIndex = rxIndex;
    }
    //***************************************************************************************
    public void IncrementRxIndex() {
        RxIndex++;
    }
    //***************************************************************************************
    public int getCommand() {
        return Command;
    }
    //***************************************************************************************
    public void setCommand(int command) {
        Command = command;
    }
    //***************************************************************************************
    public void setArgsLen(int len)
    {
        Args = new byte[len];
    }
    //***************************************************************************************
    //***************************************************************************************
    public void AddData(byte data)
    {

    }

    //***************************************************************************************
    public int getRxArgsLen() {
        return RxArgsLen;
    }
    //***************************************************************************************
    public void setRxArgsLen(int rxArgsLen) {
        RxArgsLen = rxArgsLen;
    }
    //***************************************************************************************
    public void Reset()
    {
        RxState = 0;
    }



    public int getCounter() {
        return Counter;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }




}
