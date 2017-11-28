package com.dastudio.mobilesafe.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;

import com.dastudio.mobilesafe.R;

/**
 * Created by Tony on 2017/11/27.
 */

public class RateDialog extends Dialog {

    public RateDialog(@NonNull Context context) {
        super(context, R.style.RateDialogStyle);
    }

    @Override
    public void show() {
        super.show();
        //获取布局参数
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        //修改gravity 让窗口靠着父容器的底部
        layoutParams.gravity = Gravity.BOTTOM;
        //把修改好的布局容器 设置的窗口中
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratedialog);
    }



}
