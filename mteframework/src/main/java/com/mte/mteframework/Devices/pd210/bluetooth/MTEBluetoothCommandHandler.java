package com.mte.mteframework.Devices.pd210.bluetooth;

public class MTEBluetoothCommandHandler
{
    public enum ProcessState{Idle, Pending,Completed}

    private boolean Enabled;
    private boolean Busy;
    private int Command;
    private int Sequence;
    private int ResponseExpected;
    private int AlternateCommand;




    private ProcessState State;
    //===================================================================
    //===================================================================
    public MTEBluetoothCommandHandler()
    {}
    //===================================================================
    public MTEBluetoothCommandHandler(int command)
    {this.Command = command;
    }
    //===================================================================

    //===================================================================
    public void Reset()
    {
        this.Sequence = 0;
    }
    //===================================================================
    //===================================================================
    public void setNextSequence()
    {
        this.Sequence++;
    }

    //===================================================================
    //===================================================================

    public boolean isBusy() {
        return Busy;
    }

    public void setBusy(boolean busy) {
        Busy = busy;
    }

    public int getCommand() {
        return Command;
    }

    public void setCommand(int command) {
        Command = command;
    }

    public int getSequence() {
        return Sequence;
    }

    public void setSequence(int sequence) {
        Sequence = sequence;
    }

    public int getResponseExpected() {
        return ResponseExpected;
    }

    public void setResponseExpected(int responseExpected) {
        ResponseExpected = responseExpected;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }
    public ProcessState getState() {
        return State;
    }

    public void setState(ProcessState state) {
        State = state;
    }
//===================================================================
    //===================================================================
    public int getAlternateCommand() {
        return AlternateCommand;
    }

    public void setAlternateCommand(int alternateCommand) {
        AlternateCommand = alternateCommand;
    }



    //===================================================================
    //===================================================================


}
