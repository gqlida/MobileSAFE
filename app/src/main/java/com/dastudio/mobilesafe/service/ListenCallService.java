package com.dastudio.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.dastudio.mobilesafe.utils.AddressDbUtils;
import com.dastudio.mobilesafe.utils.CustomToastUtils;

public class ListenCallService extends Service {

    private TelephonyManager mTelephonyManager;
    private MyPhoneStateListener mMyPhoneStateListener;

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
                    CustomToastUtils.cancelToast(getApplicationContext());
                    break;
            }
        }
    }
    //service销毁的时候，一定要记得注销
    @Override
    public void onDestroy() {
        super.onDestroy();
        mTelephonyManager.listen(mMyPhoneStateListener,PhoneStateListener.LISTEN_NONE);
    }


//    private void MyRe
}
