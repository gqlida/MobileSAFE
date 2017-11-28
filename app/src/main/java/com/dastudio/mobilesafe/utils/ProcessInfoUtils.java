package com.dastudio.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.dastudio.mobilesafe.R;
import com.dastudio.mobilesafe.bean.appInfo;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Tony on 2017/11/25.
 */

public class ProcessInfoUtils {


    private static String sPackageName;

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

    public static long getTotalMem(Context context){
        //还是拿到activityManager 通过activitymanager拿到内存的信息

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMem = memoryInfo.totalMem;

        return totalMem;
    }

    public static long getAvaiMem(Context context){

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        long availMem = memoryInfo.availMem;

        return availMem;
    }


    public static List<appInfo> getAllProcessInfo(Context context){

        ArrayList<appInfo> appinfo = new ArrayList<>();

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();

        //获取正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        for (int i = 0; i < runningAppProcesses.size(); i++) {

            ActivityManager.RunningAppProcessInfo processInfo = runningAppProcesses.get(i);

            String processName = processInfo.processName;
            //使用packageManager获取每一个应用程序的application对象
            boolean isSys = false;
            Drawable appIcon = null;
            String appName = "";
            int totalPssBytes = 0;

            try {

                //使用applicationInfo 获取图标  名字 是否为系统进程    pm
                ApplicationInfo applicationInfo = pm.getApplicationInfo(processName, 0);
                appIcon = applicationInfo.loadIcon(pm);
                appName = applicationInfo.loadLabel(pm).toString();
                sPackageName = applicationInfo.packageName;

                if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM) == applicationInfo.FLAG_SYSTEM) {
                    isSys = true;
                }else{
                    isSys = false;
                }

                int pid = processInfo.pid;
                //使用 am来获取 内存的占用空间
                int[] pids = {pid};
                Debug.MemoryInfo[] processMemoryInfo = am.getProcessMemoryInfo(pids);
                Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
                totalPssBytes = memoryInfo.getTotalPss() * 1024;

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                appIcon = context.getResources().getDrawable(R.mipmap.ic_launcher);
                appName = "Android";
                isSys = true;

            } finally {
                appInfo info = new appInfo(appIcon, appName, totalPssBytes, isSys, false,sPackageName);
                appinfo.add(info);
            }
        }
        return appinfo;

    }


    public static List<appInfo> getInstalledAppInfo(Context context){

        ArrayList<appInfo> appInfos = new ArrayList<>();

//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager packageManager = context.getPackageManager();

        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);

        for (ApplicationInfo installedApplication : installedApplications) {


                Drawable appIcon = installedApplication.loadIcon(packageManager);
                String appName = installedApplication.loadLabel(packageManager).toString();
                String packageName = installedApplication.packageName;

                boolean isSys = true;
                if ((installedApplication.flags & installedApplication.FLAG_SYSTEM) == installedApplication.FLAG_SYSTEM) {
                    isSys = true;
                }else{
                    isSys = false;
                }

                appInfo appInfo = new appInfo(appIcon, appName, 80, isSys, false,packageName);

                appInfos.add(appInfo);

        }

        return appInfos;
    }


}
