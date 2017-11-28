package com.dastudio.mobilesafe.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class UninstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "!!!!!!!!!!!AAAA", Toast.LENGTH_SHORT).show();
        String action = intent.getAction();

        if ("android.intent.action.PACKAGE_ADDED".equals(action)){
            System.out.println("应用被安装了11111");
        }else if ("android.intent.action.PACKAGE_INSTALL".equals(action)){
            System.out.println("应用被安装了22222");
        }else if ("android.intent.action.PACKAGE_REMOVED".equals(action)){
            System.out.println("应用被卸载了");
        }
    }
}
