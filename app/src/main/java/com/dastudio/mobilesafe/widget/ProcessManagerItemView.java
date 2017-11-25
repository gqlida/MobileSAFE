package com.dastudio.mobilesafe.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;

/**
 * Created by Tony on 2017/11/25.
 */

public class ProcessManagerItemView extends LinearLayout {

    private Context context;
    private ImageView mProcessIcon;
    private TextView mProcessName;
    private TextView mProcessLeftText;
    private TextView mProcessRightText;
    private ProgressBar mProgressBar;

    public ProcessManagerItemView(Context context) {
        this(context,null);
    }

    public ProcessManagerItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProcessManagerItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @SuppressLint("CutPasteId")
    public void initView(){

        View view = View.inflate(context, R.layout.layout_process_manager_item_view,null);
        mProcessIcon = view.findViewById(R.id.iv_process_icon);
        mProcessName = view.findViewById(R.id.tv_process_name);
        mProcessLeftText = view.findViewById(R.id.tv_process_left_text);
        mProcessRightText = view.findViewById(R.id.tv_process_right_text);
        mProgressBar = view.findViewById(R.id.progressBar);
        addView(view);
    }

    public void setProcessIcon(int resId){
        mProcessIcon.setImageResource(resId);
    }

    public void setProcessName(String str){
        mProcessName.setText(str);
    }

    public void setProcessLeftText(String str){
        mProcessLeftText.setText(str);
    }

    public void setProcessRightText(String str){
        mProcessRightText.setText(str);
    }

    public void setProgressBar(int process,int max){
       mProgressBar.setProgress(process);
       mProgressBar.setMax(max);
    }

}
