package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Process;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;
import com.dastudio.mobilesafe.bean.appInfo;
import com.dastudio.mobilesafe.utils.ProcessInfoUtils;
import com.dastudio.mobilesafe.widget.ProcessManagerItemView;


import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class ProcessActivity extends AppCompatActivity {


    private ProcessManagerItemView mPmivProcess;
    private ProcessManagerItemView mPmivMemory;
    private ListView mProcessList;
    private ProgressBar mProgressLoading;
    private Button mProcessClear;
    private CheckBox mCheckBoxSelect;
    private List<appInfo> mAllProcessInfo;
    private List<appInfo> mInstalledAppInfos;
    private ArrayList<appInfo> mSysInstallAppInfo;
    private ArrayList<appInfo> mCustomerInstallAppInfo;
    private static final int ITEM_TEXT = 0;
    private static final int ITEM_TEXT_IMG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

        ActionBar ActionBar = getSupportActionBar();
        assert ActionBar != null;
        if (Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(0);
        }
        ActionBar.setDisplayHomeAsUpEnabled(true);
        ActionBar.setTitle("Process");

        findviews();

        initProcess();
        initMemory();

        loadThread();

    }

    private void loadThread() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取用户安装所有应用的信息
                mInstalledAppInfos = ProcessInfoUtils.getInstalledAppInfo(ProcessActivity.this);
                mCustomerInstallAppInfo = new ArrayList<>();
                mSysInstallAppInfo = new ArrayList<>();

                for (int i = 0; i < mInstalledAppInfos.size() ; i++) {

                    appInfo appInfo = mInstalledAppInfos.get(i);

                    if (!appInfo.isSys()){
                        mCustomerInstallAppInfo.add(appInfo);
                    }else{
                        mSysInstallAppInfo.add(appInfo);
                    }
                }

                mInstalledAppInfos.clear();
                mInstalledAppInfos.addAll(mCustomerInstallAppInfo);
                mInstalledAppInfos.addAll(mSysInstallAppInfo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressLoading.setVisibility(View.GONE);
                        final packageAdapter packageAdapter = new packageAdapter();
                        mProcessList.setAdapter(packageAdapter);
                        mProcessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                appInfo appInfo = mInstalledAppInfos.get(i);
                                appInfo.setCheck(!appInfo.isCheck());
                                packageAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                });
            }
        }).start();
    }

    static class ViewHolder{
        ImageView appIcon;
        TextView appName;
        TextView appMemSize;
        CheckBox isSelect;
    }

    static class ViewTitleHolder{
        TextView processTitle;
        TextView processNumber;
    }



    class packageAdapter extends BaseAdapter{

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int i) {

            if (i == 0 || i == mCustomerInstallAppInfo.size()+1){
                return ITEM_TEXT;
            }else{
                return ITEM_TEXT_IMG;
            }
        }

        @Override
        public int getCount() {
            return mInstalledAppInfos.size();
        }

        @Override
        public appInfo getItem(int i) {
            return mInstalledAppInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {

            ViewHolder viewHolder = null;

            int itemViewType = getItemViewType(i);

            if (itemViewType == ITEM_TEXT) {

                View view = null;
                if (convertview == null) {

                    view = View.inflate(ProcessActivity.this, R.layout.item_process_title, null);

                }else{

                    view = convertview;

                    TextView processTitle = convertview.findViewById(R.id.tv_process_title);
                    TextView processumber = convertview.findViewById(R.id.tv_process_number);

                    if (i == 0) {
                        processTitle.setText("InstallApplication:");
                        processumber.setText(mCustomerInstallAppInfo.size()+"");
                    }else{
                        processTitle.setText("SystemApplication:");
                        processumber.setText(mSysInstallAppInfo.size()+"");
                    }

                }

                return view;

            } else {
                if (convertview == null) {
                    convertview = View.inflate(ProcessActivity.this, R.layout.item_process, null);

                    viewHolder = new ViewHolder();

                    viewHolder.appIcon = convertview.findViewById(R.id.package_icon);
                    viewHolder.appName = convertview.findViewById(R.id.package_name);
                    viewHolder.appMemSize = convertview.findViewById(R.id.package_memSize);
                    viewHolder.isSelect = convertview.findViewById(R.id.checkBox);

                    //将viewHolder挂载到convertView上，让系统帮助存储
                    convertview.setTag(viewHolder);
                } else {
                    //如果不为空的 就可以复用
                    viewHolder = (ViewHolder) convertview.getTag();
                }

                appInfo appInfo = mInstalledAppInfos.get(i-1);

                viewHolder.appIcon.setImageDrawable(mInstalledAppInfos.get(i).getDrawable());
                viewHolder.appName.setText(mInstalledAppInfos.get(i).getName());
                viewHolder.appMemSize.setText("50MB");

                if (!appInfo.isSys()) {
                    viewHolder.isSelect.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.isSelect.setVisibility(View.GONE);
                }

                return convertview;
            }
        }
    }



    private void initProcess() {
        int runningProcessCount = ProcessInfoUtils.getRunningProcessCount(this);
        int allProcess = ProcessInfoUtils.getAllProcess(this);

        mPmivProcess.setProcessName("Process");
        mPmivProcess.setProcessLeftText(runningProcessCount+"");
        mPmivProcess.setProcessRightText(allProcess+"");

        int progress = runningProcessCount * 100 / allProcess;

        mPmivProcess.setProgressBar(progress);

    }

    private void initMemory() {

        long totalMem = ProcessInfoUtils.getTotalMem(this);
        long avaiMem = ProcessInfoUtils.getAvaiMem(this);

        long usedMem = totalMem - avaiMem;
        //Formatter  text.format  用于计算内存  1024单位的大小
        String totalNum = Formatter.formatFileSize(this, totalMem);
        String usedNum = Formatter.formatFileSize(this, usedMem);

        mPmivMemory.setProcessName("Memory");
        mPmivMemory.setProcessRightText(totalNum);
        mPmivMemory.setProcessLeftText(usedNum);


        long progress = (usedMem * 100 / totalMem);

        mPmivMemory.setProgressBar((int) progress);

    }

    private void findviews() {

        mPmivProcess = findViewById(R.id.pmiv_process);
        mPmivMemory = findViewById(R.id.pmiv_memory);
        mProcessList = findViewById(R.id.lv_process);
        mProgressLoading = findViewById(R.id.progress_loading);
        mProcessClear = findViewById(R.id.btn_process_clear);
        mCheckBoxSelect = findViewById(R.id.cb_process_select);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


//    class MyStickListHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter{
//
//        @Override
//        public View getHeaderView(int position, View convertView, ViewGroup parent) {
//
//            ViewTitleHolder viewTitleHolder = null;
//
//                if (convertView == null) {
//
//                    convertView = View.inflate(ProcessActivity.this,R.layout.item_process_title,null);
//
//                    viewTitleHolder = new ViewTitleHolder();
//
//                    viewTitleHolder.processTitle = convertView.findViewById(R.id.tv_process_title);
//                    viewTitleHolder.processNumber = convertView.findViewById(R.id.tv_process_number);
//
//                    convertView.setTag(viewTitleHolder);
//
//                }else{
//                    viewTitleHolder = (ViewTitleHolder) convertView.getTag();
//                }
//
//                appInfo appInfo = mInstalledAppInfos.get(position);
//
//                if (appInfo.isSys()) {
//                    viewTitleHolder.processTitle.setText("InstallApplication");
//                    viewTitleHolder.processNumber.setText(mCustomerInstallAppInfo.size()+"");
//                }else{
//                    viewTitleHolder.processTitle.setText("SystemApplication");
//                    viewTitleHolder.processNumber.setText(mInstalledAppInfos.size()+"");
//                }
//
//            return convertView;
//        }
//
//        @Override
//        public long getHeaderId(int position) {
//            appInfo appInfo = mInstalledAppInfos.get(position);
//                if (appInfo.isSys()){
//                    return 0;
//                }else{
//                    return 1;
//                }
//        }
//
//        @Override
//        public int getCount() {
//            return mInstalledAppInfos.size();
//        }
//
//        @Override
//        public appInfo getItem(int i) {
//            return mInstalledAppInfos.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View convertview, ViewGroup viewGroup) {
//            ViewHolder viewHolder = null;
//
//            if (convertview == null) {
//
//                convertview = View.inflate(ProcessActivity.this,R.layout.item_process,null);
//
//                viewHolder = new ViewHolder();
//
//                viewHolder.appIcon = convertview.findViewById(R.id.package_icon);
//                viewHolder.appName = convertview.findViewById(R.id.package_name);
//                viewHolder.appMemSize = convertview.findViewById(R.id.package_memSize);
//                viewHolder.isSelect = convertview.findViewById(R.id.cb_process_select);
//
//                convertview.setTag(viewHolder);
//            }else{
//                viewHolder = (ViewHolder) convertview.getTag();
//            }
//
//            viewHolder.appIcon.setImageDrawable(mInstalledAppInfos.get(i).getDrawable());
//            viewHolder.appName.setText(mInstalledAppInfos.get(i).getName());
//            viewHolder.appMemSize.setText("50MB");
////            viewHolder.isSelect.setChecked(false);
//
//
//            return convertview;
//        }
//    }




    
    
    




}
