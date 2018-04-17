package com.tal.abctime.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by irene on 2018/4/16.
 */

public class DeviceInfo {
    public String getDisplayInfo(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Log.d("sy__test","Density is "+displayMetrics.density+" densityDpi is "+displayMetrics.densityDpi+" height: "+displayMetrics.heightPixels+
                " width: "+displayMetrics.widthPixels);
        return "Density is "+displayMetrics.density+" densityDpi is "+displayMetrics.densityDpi+" height: "+displayMetrics.heightPixels+
                " width: "+displayMetrics.widthPixels;
    }
}
