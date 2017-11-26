package com.dastudio.mobilesafe.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Tony on 2017/11/25.
 */

public class ProcessInfoUtils {

    public static int getRunningProcessCount(Context context){

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        return runningAppProcesses.size();
    }

    public static int getAllProcess(Context context){
        //获取包的管理者对象
        PackageManager packageManager = context.getPackageManager();
        //获取手机安装的包总数
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(
                PackageManager.GET_ACTIVITIES | PackageManager.GET_RECEIVERS
                | PackageManager.GET_SERVICES | PackageManager.GET_PROVIDERS);
        HashSet<String> processList = new HashSet<>();
        //循环遍历上述集合，获取安装在手机上的每一个应用程序，获取四大组件和application中的process
        for (PackageInfo packageInfo : installedPackages) {
            //application中配置的process名称
            String processName = packageInfo.applicationInfo.processName;
            processList.add(processName);
            //多个activity中的processName
            ActivityInfo[] activities = packageInfo.activities;

            if (activities != null) {
                for (ActivityInfo activity : activities) {
                    processList.add(activity.processName);
                }
            }

            ProviderInfo[] providers = packageInfo.providers;

            if (providers != null) {
                for (ProviderInfo provider : providers) {
                    processList.add(provider.processName);
                }

            }

            ServiceInfo[] services = packageInfo.services;

            if (services != null) {

                for (ServiceInfo service : services) {
                    processList.add(service.processName);
                }
            }

            ActivityInfo[] receivers = packageInfo.receivers;

            if (receivers != null) {
                for (ActivityInfo receiver : receivers) {
                    processList.add(receiver.processName);
                }
            }


        }

        return processList.size();
    }







}
