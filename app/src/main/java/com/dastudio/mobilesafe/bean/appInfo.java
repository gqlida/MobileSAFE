package com.dastudio.mobilesafe.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Tony on 2017/11/26.
 */

public class appInfo {

    private Drawable mDrawable;
    private String name;
    private int memSize;
    private boolean isSys;
    private boolean isCheck;


    public appInfo(Drawable drawable, String name, int memSize, boolean isSys, boolean isCheck) {
        mDrawable = drawable;
        this.name = name;
        this.memSize = memSize;
        this.isSys = isSys;
        this.isCheck = isCheck;
    }


    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemSize() {
        return memSize;
    }

    public void setMemSize(int memSize) {
        this.memSize = memSize;
    }

    public boolean isSys() {
        return isSys;
    }

    public void setSys(boolean sys) {
        isSys = sys;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
