package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;


public class ProcessActivity extends AppCompatActivity {

    private ProcessManagerItemView mPmivProcess;
    private ProcessManagerItemView mPmivMemory;
    private ListView mProcessList;
    private ProgressBar mProgressLoading;
    private Button mProcessClear;
    private CheckBox mCheckBoxSelect;
    private List<appInfo> mAllProcessInfo;
    private List<appInfo> mInstalledAppInfos;

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



        new Thread(new Runnable() {



            @Override
            public void run() {
                mInstalledAppInfos = ProcessInfoUtils.getInstalledAppInfo(ProcessActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressLoading.setVisibility(View.GONE);
                        mProcessList.setAdapter(new packageAdapter());
                    }
                });
            }
        }).start();





    }

    class packageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mInstalledAppInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {
            View view;
            if (convertview == null){
                view = View.inflate(ProcessActivity.this, R.layout.item_process, null);
            }else{
                view = convertview;
            }

            ImageView appIcon = view.findViewById(R.id.package_icon);
            TextView appName = view.findViewById(R.id.package_name);
            TextView appMemSize = view.findViewById(R.id.package_memSize);
            CheckBox isSelect = view.findViewById(R.id.cb_process_select);

            appIcon.setImageDrawable(mInstalledAppInfos.get(i).getDrawable());
            appName.setText(mInstalledAppInfos.get(i).getName());
            appMemSize.setText("50MB");

            return view;
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

    
    
    




}
