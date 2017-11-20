package com.dastudio.mobilesafe.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dastudio.mobilesafe.R;

public class SettingActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("@string/setting_name");


        ListView ll_setting = findViewById(R.id.ll_setting);
        ll_setting.setAdapter(new MyAdapter());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 3;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(),R.layout.item_setting,null);
            }else{
                view = convertView;
            }



            return view;
        }

    }

}
