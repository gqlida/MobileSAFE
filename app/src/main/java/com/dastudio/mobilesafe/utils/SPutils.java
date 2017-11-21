package com.dastudio.mobilesafe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tony on 2017/11/21.
 */

public class SPutils {


    private static SharedPreferences mSharedPreferences;

    public static void getSP(Context context){
        if (mSharedPreferences != null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
    }

    public static void putString(Context context, String key, String value){
        getSP(context);
        mSharedPreferences.edit().putString(key,value).apply();
    }
    public static void putInt(Context context,String key,int value){
        getSP(context);
        mSharedPreferences.edit().putInt(key,value).apply();
    }
    public static void putBoolean(Context context,String key,boolean value){
        getSP(context);
        mSharedPreferences.edit().putBoolean(key,value).apply();
    }

    public static String getString(Context context, String key, String defvalue){
        getSP(context);
        return mSharedPreferences.getString(key,defvalue);
    }

    public static int getInt(Context context,String key,int defvalue){
        getSP(context);
        return mSharedPreferences.getInt(key,defvalue);
    }

    public static boolean getBoolean(Context context,String key,boolean defvalue){
        getSP(context);
        return mSharedPreferences.getBoolean(key,defvalue);
    }
}
