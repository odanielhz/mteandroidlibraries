package com.mte.mteframework.Devices.pd210.bluetooth;

public class PD210SmartDeviceConstants
{

    //================================================================================================
    //Constant
    public static final byte IOT_NO_COMMAND =                                       0x00;
    public static final byte IOT_COMMAND_GET_WEIGHT_STRING =                        0x01;
    public static final byte IOT_COMMAND_GET_WEIGHT_STRUCTURED_DATA =               0x02;
    public static final byte IOT_COMMAND_GET_DISPLAY_STRING =                       0x03;


    public static final byte IOT_COMMAND_SET_KEYBOARD_KEY  =                        0x10;

    public static final byte IOT_COMMAND_PRINT_TICKET_DATA =                        0x70;

    //==============================================================================================
    //KEYS

    public static final byte  MTE_PD210_KEYPAD_KEY_1	    =				17;		//0x11
    public static final byte  MTE_PD210_KEYPAD_KEY_2		=				33;		//0x21
    public static final byte  MTE_PD210_KEYPAD_KEY_3		=				49;		//0x31
    public static final byte  MTE_PD210_KEYPAD_KEY_4		=				18;		//0x12
    public static final byte  MTE_PD210_KEYPAD_KEY_5		=				34;		//0x22
    public static final byte  MTE_PD210_KEYPAD_KEY_6		=				50;		//0x32
    public static final byte  MTE_PD210_KEYPAD_KEY_7		=				19;		//0x13
    public static final byte  MTE_PD210_KEYPAD_KEY_8		=				35;		//0x23
    public static final byte  MTE_PD210_KEYPAD_KEY_9		=				51;		//0x33
    public static final byte  MTE_PD210_KEYPAD_KEY_0		=				67;		//0x43
    public static final byte  MTE_PD210_KEYPAD_KEY_DP		=			    66;		//0x42
    public static final byte  MTE_PD210_KEYPAD_KEY_CLEAR	=				65;		//0x41

    public static final byte  MTE_PD210_KEYPAD_KEY_TARE		=			    97;		//0x61
    public static final byte  MTE_PD210_KEYPAD_KEY_ZERO		=			    81;		//0x51
    public static final byte  MTE_PD210_KEYPAD_KEY_PRINT	=				98;		//0x62
    public static final byte  MTE_PD210_KEYPAD_KEY_UNIT		=			    82;		//0x52
    public static final byte  MTE_PD210_KEYPAD_KEY_PIECES	=			    83;		//0x53
    public static final byte  MTE_PD210_KEYPAD_KEY_MODE		=			    99;		//0x63


    //==============================================================================
    //DEVICE PROTOCOL TYPE
    public static final int MTE_PD210_BLUETOOTH_DEFAULT_PROTOCOL            =0;
    public static final int MTE_PD210_BLUETOOTH_ESP32_PROTOCOL              =1;
    public static final int MTE_PD210_BLUETOOTH_ESP32_RAW_PROTOCOL          =2;
    public static final int MTE_PD210_BLUETOOTH_ESP32_INTERFACE_PROTOCOL    =20;


}
