package com.dastudio.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by Tony on 2017/11/23.
 */

public class CloseUtils {
    //关闭流
    public static void closeStream(Closeable ...closeables){
        if(closeables != null && closeables.length > 0){
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //关闭服务  serviceName 当前运行服务的名字
    public static boolean isServiceRunning(Context context, String serviceName){

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(2000);
        if (runningServices != null && runningServices.size() > 0) {
            for (ActivityManager.RunningServiceInfo runningService : runningServices) {
                ComponentName service = runningService.service;
                String className1 = service.getClassName();
                if (className1.equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
