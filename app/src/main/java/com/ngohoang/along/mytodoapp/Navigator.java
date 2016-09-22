package com.ngohoang.along.mytodoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ngohoang.along.mytodoapp.ui.detail.DetailActivity;

/**
 * Created by Admin on 21/09/2016.
 */
public class Navigator {
    private static Navigator ourInstance = new Navigator();

    public static Navigator getInstance() {
        return ourInstance;
    }

    private Navigator() {
    }

    public void navigateToAddItem(Context context) {
        if (context != null) {
            Intent intentToLaunch = DetailActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToAddItem(Activity activity,int requestCode) {
        if (activity != null) {
            Intent intentToLaunch = DetailActivity.getCallingIntent(activity);
            activity.startActivityForResult(intentToLaunch,requestCode);
        }
    }

}
