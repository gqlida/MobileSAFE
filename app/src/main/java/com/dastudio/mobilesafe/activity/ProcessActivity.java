package com.dastudio.mobilesafe.activity;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dastudio.mobilesafe.R;
import com.dastudio.mobilesafe.widget.ProcessManagerItemView;

public class ProcessActivity extends AppCompatActivity {

    private ProcessManagerItemView mPmivProcess;
    private ProcessManagerItemView mPmivMemory;
    private ListView mProcessList;
    private ProgressBar mProgressLoading;
    private Button mProcessClear;
    private CheckBox mCheckBoxSelect;

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
    }

    private void initProcess() {
        int runningProcessCount = ProcessInfoUtils.getRunningProcessCount(this);
        int allProcess = ProcessInfoUtils.getAllProcess(this);


        mPmivProcess.setProcessName("Process");
        mPmivProcess.setProcessLeftText(runningProcessCount+"");
        mPmivProcess.setProcessRightText(allProcess+"");



        mPmivProcess.setProgressBar(runningProcessCount,allProcess);


    }

    private void initMemory() {













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
