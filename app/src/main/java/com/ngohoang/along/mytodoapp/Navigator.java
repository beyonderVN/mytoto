package com.ngohoang.along.mytodoapp;

/**
 * Created by Admin on 21/09/2016.
 */
public class Navigator {
    public static final String TAG = "Navigator";
    public static final String TYPE_ADD = "TYPE_ADD";
    public static final String TYPE_EDIT = "TYPE_EDIT";
    public static final String TYPE_REMOVE = "TYPE_REMOVE";


    private static Navigator ourInstance = new Navigator();

    public static Navigator getInstance() {
        return ourInstance;
    }

    private Navigator() {
    }

}
