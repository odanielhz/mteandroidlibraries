package com.mte.mteframework.views.recyclerview.bluetoothdevices;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mte.mteframework.Debug.MTEDebugLogger;
import com.mte.mteframework.Devices.pd210.bluetooth.PD210SmartDeviceBluetooth;
import com.mte.mteframework.R;
import com.mte.mteframework.views.recyclerview.typeA.ListAdapaterTypeA;
import com.mte.mteframework.views.recyclerview.typeA.ListItemsTypeA;

import java.util.ArrayList;
import java.util.List;

public class MTEBluetoothAdapter extends RecyclerView.Adapter<MTEBluetoothAdapter.MTEBluetoothViewHolder>
        implements View.OnClickListener
{

    private ArrayList<MTEBluetoothDeviceItem> mData;
    private int layoutResource;
    private View.OnClickListener listener;
    Context mContext;

    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData) {
        this.mData = mData;
        this.layoutResource = R.layout.mte_bl_device_list_layout_type1;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData, Context context) {
        this.mData = mData;
        this.layoutResource = R.layout.mte_bl_device_list_layout_type1;
        mContext = context;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData, int layoutResource) {
        this.mData = mData;
        this.layoutResource = layoutResource;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData, Context context, int layoutResource) {
        this.mData = mData;
        this.layoutResource = layoutResource;
        mContext = context;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public void setContext(Context context)
    {
        mContext = context;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public Context getContext() {
        return mContext;
    }
    //***********************************************************************************************
    //***********************************************************************************************
    @NonNull
    @Override
    public MTEBluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if(this.layoutResource !=0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(this.layoutResource, null, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mte_bl_device_list_layout_type1, null, false);
        }
        //set on click listener
        view.setOnClickListener(this);

        return new MTEBluetoothViewHolder(view);
    }
    //***********************************************************************************************
    //***********************************************************************************************
    @Override
    public void onBindViewHolder(@NonNull MTEBluetoothViewHolder holder, int position)
    {


        holder.dev_img.setImageResource(mData.get(position).getImage());
        holder.dev_name.setText(mData.get(position).getName());
        holder.dev_address.setText(mData.get(position).getAddress());
        if(mData.get(position).getBluetoothType() == PD210SmartDeviceBluetooth.BluetoothDeviceType.BLUETOOTH_CLASSIC)
        {
            //classsic
            holder.dev_type.setText("Classic Bluetooth");
            holder.dev_type.setTextColor(Color.BLACK);
        }
        else
        {
            //ble device
            holder.dev_type.setText("BLE");
            holder.dev_type.setTextColor( Color.BLUE);
        }


    }
    //**********************************************************************
    //**********************************************************************
    public void setItems(ArrayList<MTEBluetoothDeviceItem> items){mData = items;}
    //=====================================================================================================================
    //=====================================================================================================================

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    //=====================================================================================================================
    //=====================================================================================================================
    @Override
    public void onClick(View v)
    {
        if(this.listener !=null)
        {
            listener.onClick(v);
        }
    }
    //***********************************************************************************************
    //***********************************************************************************************
    @Override
    public int getItemCount()
    {
        return (mData == null)?0:mData.size();
    }
    //***********************************************************************************************
    //***********************************************************************************************
    public class MTEBluetoothViewHolder  extends RecyclerView.ViewHolder
    {
        public ImageView dev_img;
        public TextView dev_name,dev_address,dev_type;


        //=====================================================================
        //=====================================================================
        public MTEBluetoothViewHolder(@NonNull View itemView)
        {

            super(itemView);
            //MTEDebugLogger.Log(true,"MTE-BTViewHolder Class","ViewHolder");
            this.dev_img = itemView.findViewById(R.id.dev_img);
            this.dev_name = itemView.findViewById(R.id.dev_name);
            this.dev_address =  itemView.findViewById(R.id.dev_address);
            this.dev_type = itemView.findViewById(R.id.dev_type);

        }

    }
    //***********************************************************************************************
    //***********************************************************************************************
}
