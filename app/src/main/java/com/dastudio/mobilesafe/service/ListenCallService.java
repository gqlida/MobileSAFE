package com.dastudio.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.dastudio.mobilesafe.utils.AddressDbUtils;
import com.dastudio.mobilesafe.utils.CustomToastUtils;

import java.util.concurrent.BrokenBarrierException;

public class ListenCallService extends Service {

    private TelephonyManager mTelephonyManager;
    private MyPhoneStateListener mMyPhoneStateListener;
    private MyReceiver mMyReceiver;

    public ListenCallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //电话管理模拟器
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        mMyPhoneStateListener = new MyPhoneStateListener();
        mTelephonyManager.listen(mMyPhoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);

        //动态的注册广播接收者  清单文件设置action 和 注册接收者
        mMyReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(mMyReceiver,intentFilter);

    }


    private class MyPhoneStateListener extends PhoneStateListener{

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    CustomToastUtils.cancelToast(getApplicationContext());
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = AddressDbUtils.getAddress(getApplicationContext(), incomingNumber);
                    CustomToastUtils.showToast(getApplicationContext(),address);
//                    Toast.makeText(ListenCallService.this, address, Toast.LENGTH_LONG).show();
//                    incomingNumber
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }
    }
    //service销毁的时候，一定要记得注销
    @Override
    public void onDestroy() {
        super.onDestroy();
        mTelephonyManager.listen(mMyPhoneStateListener,PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(mMyReceiver);
    }


    private class MyReceiver extends BroadcastReceiver{
        //动态设置广播需要设置onReceive方法
        @Override
        public void onReceive(Context context, Intent intent) {

            String outGoingNumber = getResultData();

            String address = AddressDbUtils.getAddress(context, outGoingNumber);

            CustomToastUtils.showToast(context,address);
        }
    }
}
