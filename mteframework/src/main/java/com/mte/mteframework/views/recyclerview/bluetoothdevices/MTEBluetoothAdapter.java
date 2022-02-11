package com.mte.mteframework.views.recyclerview.bluetoothdevices;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mte.mteframework.Debug.MTEDebugLogger;
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

    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData) {
        this.mData = mData;
        this.layoutResource = R.layout.mte_bl_device_list_layout_type1;
    }

    //***********************************************************************************************
    //***********************************************************************************************
    public MTEBluetoothAdapter(ArrayList<MTEBluetoothDeviceItem> mData, int layoutResource) {
        this.mData = mData;
        this.layoutResource = layoutResource;
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
        ImageView dev_img;
        TextView dev_name,dev_address;

        //=====================================================================
        //=====================================================================
        public MTEBluetoothViewHolder(@NonNull View itemView)
        {

            super(itemView);
            //MTEDebugLogger.Log(true,"MTE-BTViewHolder Class","ViewHolder");
            this.dev_img = itemView.findViewById(R.id.dev_img);
            this.dev_name = itemView.findViewById(R.id.dev_name);
            this.dev_address =  itemView.findViewById(R.id.dev_address);

        }

    }
    //***********************************************************************************************
    //***********************************************************************************************
}
