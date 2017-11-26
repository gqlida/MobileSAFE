package com.dastudio.mobilesafe.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dastudio.mobilesafe.R;

/**
 * Created by Tony on 2017/11/24.
 */

public class CustomToastUtils {

    private static View sView;

    public static void showToast(Context context, String address){

        sView = View.inflate(context, R.layout.layout_toast, null);
        TextView toast_text = sView.findViewById(R.id.toast_text);

        toast_text.setText(address);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //width height format type flags
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height  = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;

        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        windowManager.addView(sView,layoutParams);
        sView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //TODO onTouch
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return  true;
            }
        });

    }


    public static void cancelToast(Context context){
        if (sView != null && sView.getParent() != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeViewImmediate(sView);
            sView = null;
        }
    }




}
