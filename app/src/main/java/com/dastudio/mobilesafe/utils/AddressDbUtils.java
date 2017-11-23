package com.dastudio.mobilesafe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tony on 2017/11/23.
 */

public class AddressDbUtils {

    public static String getAddress(Context context,String number){


        String result = "null";
        String path = context.getFilesDir().getAbsolutePath()+"/address.db";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        //TODO 正则表达式
        if (number.matches("^1[35678]\\d{9}$")) {
            String number_7 = number.substring(0,7);
            @SuppressLint("Recycle")
            Cursor cursor = db.query("info", new String[]{"cardtype"}, "mobileprefix = ?", new String[]{number_7}, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                result = cursor.getString(0);
            }
            cursor.close();
        }else{
            int length = number.length();
            switch (length) {
                case 3:
                    result = "区号";
                    break;
                case 4:
                    result = "模拟号";
                    break;
                case 5:
                    result = "服务号码";
                    break;
                case 7:
                case 8:
                    result = "本地固话";
                    break;
                case 11:
                    String number_3 = number.substring(0, 3);
                    String sql = "select city from info where area = ?";
                    Cursor cursor = db.rawQuery(sql, new String[]{number_3});
                    if (cursor != null && cursor.moveToNext()){
                        result = cursor.getString(0);
                        cursor.close();
                        return "归属地:"+result;
                    }

                    number_3 = number.substring(0, 4);
                    cursor = db.rawQuery(sql, new String[]{number_3});
                    if (cursor != null && cursor.moveToNext()){
                        result = cursor.getString(0);
                        return "归属地:"+result;
                    }
                    break;
            }
        }

        return "归属地：" + result;
    }



}
