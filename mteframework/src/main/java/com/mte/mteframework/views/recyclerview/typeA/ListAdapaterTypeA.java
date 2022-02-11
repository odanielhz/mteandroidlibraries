package com.mte.mteframework.views.recyclerview.typeA;

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

import java.util.ArrayList;
import java.util.List;

public class ListAdapaterTypeA extends RecyclerView.Adapter<ListAdapaterTypeA.ViewHolder>
{
    private ArrayList<ListItemsTypeA> mData;
    private int layoutResource;

    //**********************************************************************
    //**********************************************************************
    public ListAdapaterTypeA(ArrayList<ListItemsTypeA> mData) {
        this.mData = mData;

    }

    //**********************************************************************
    //**********************************************************************
    public ListAdapaterTypeA(ArrayList<ListItemsTypeA> mData, int layoutResource) {
        this.mData = mData;
        this.layoutResource = layoutResource;
    }



    //**********************************************************************
    //**********************************************************************

    @Override
    public int getItemCount()
    {
        return (this.mData == null)? 0:this.mData.size();
    }


    //**********************************************************************
    //**********************************************************************

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;
        if(this.layoutResource !=0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(this.layoutResource, null, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mteframework_recyclerview_type_a_layout, null, false);
        }
        return new ViewHolder(view);

    }



    //**********************************************************************
    //**********************************************************************
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        try
        {
            MTEDebugLogger.Log(true,"MTE-ListAdapter","onBindViewHolder");

            holder.v_img.setImageResource(mData.get(position).getImg());
            holder.txt_title.setText(mData.get(position).getTitle());
            holder.txt_description.setText(mData.get(position).getDescription());
        }
        catch(Exception ex)
        {
            MTEDebugLogger.Log(true,"MTE-ListAdapter","Exception on onBindViewHolder");
        }


    }
    //**********************************************************************
    //**********************************************************************
    public void setItems(ArrayList<ListItemsTypeA> items){mData = items;}

    //**********************************************************************
    //**********************************************************************
    //ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView v_img;
        TextView txt_title,txt_description;

        //=====================================================================
        //=====================================================================
        public ViewHolder(@NonNull View itemView)
        {

            super(itemView);
            MTEDebugLogger.Log(true,"MTE-ViewHolder Class","ViewHolder");
            this.v_img = itemView.findViewById(R.id.img);
            this.txt_title = itemView.findViewById(R.id.txtmenu);
            this.txt_description =  itemView.findViewById(R.id.txtdescription);

        }


        //=====================================================================
        //=====================================================================



    }


    //**********************************************************************
    //**********************************************************************



}
