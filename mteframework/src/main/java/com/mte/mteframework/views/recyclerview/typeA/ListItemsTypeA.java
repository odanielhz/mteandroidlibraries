package com.mte.mteframework.views.recyclerview.typeA;

import android.media.Image;

public class ListItemsTypeA
{
    public int imgresource;
    public String title;
    public String description;

    //========================================================================================
    public ListItemsTypeA() {
    }
    //========================================================================================
    public ListItemsTypeA(int img, String title, String description) {
        this.imgresource = img;
        this.title = title;
        this.description = description;
    }
    //========================================================================================
    //========================================================================================

    public int getImg() {
        return imgresource;
    }

    public void setImg(int img) {
        this.imgresource = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //========================================================================================
}
