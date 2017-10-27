package com.example.locationdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fanlat on 27/10/17.
 */

public class ViewUtils {

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context context, Throwable throwable){
        toast(context, throwable.getMessage());
    }
}
