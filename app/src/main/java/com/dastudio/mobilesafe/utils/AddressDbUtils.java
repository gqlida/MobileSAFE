package com.dastudio.mobilesafe.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tony on 2017/11/23.
 */

public class AddressDbUtils {

    public static String getAddress(Context context,String number){


        String resulte = "null";
        String path = context.getFilesDir().getAbsolutePath()+"/address.db";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        return "归属地：" + resulte;
    }



}
