package com.ngohoang.along.mytodoapp.model;

import java.io.Serializable;

/**
 * Created by Admin on 21/09/2016.
 */

public abstract class BaseItem implements Serializable{
    public static int TYPE_TODO_ITEM = 1;

    protected int itemViewType;
    public int getItemViewType(){
        return itemViewType;
    }
    abstract public void setItemViewType();
}
