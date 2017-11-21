package com.dastudio.mobilesafe.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;

public class SettingActivity extends AppCompatActivity {

    private String[] set_content = {"Auto update","Mobile track","App theme"};
//    private int[] set_icons = {,,};
//    private String[]

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.setting_name);


        ListView ll_setting = findViewById(R.id.ll_setting);
        ll_setting.setAdapter(new MyAdapter());
        ll_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case 1:

                        break;


                }
            }
        });
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
            View view = View.inflate(getApplicationContext(),R.layout.item_setting,null);

            ImageView setting_icons = view.findViewById(R.id.setting_icons);
            TextView setting_content = view.findViewById(R.id.setting_content);
            Switch setting_switch = view.findViewById(R.id.setting_switch);

            switch (position) {
                case 0:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[0]);
                    setting_switch.setVisibility(View.GONE);
                    break;
                case 1:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[1]);
                    setting_switch.setVisibility(View.GONE);
                    break;
                case 2:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[2]);
                    setting_switch.setVisibility(View.GONE);
                    break;
            }
            return view;
        }

    }

}
