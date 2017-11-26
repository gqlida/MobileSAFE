package com.dastudio.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dastudio.mobilesafe.R;
import com.dastudio.mobilesafe.service.ListenCallService;
import com.dastudio.mobilesafe.utils.CloseUtils;
import com.dastudio.mobilesafe.utils.SPutils;

public class SettingActivity extends AppCompatActivity {

    private String[] set_content = {"Auto update","Mobile track","Home Animation","App theme"};
//    private int[] set_icons = {,,};
//    private String[]

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.setting_name);


        ListView ll_setting = findViewById(R.id.ll_setting);
        ll_setting.setAdapter(new MyAdapter());
        ll_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(SettingActivity.this, "hello", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        break;
                    case 2:
                        Toast.makeText(SettingActivity.this, "hello", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
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
            return 4;
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
                    setting_switch.setVisibility(View.VISIBLE);
                    break;
                case 1:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[1]);
                    setting_switch.setVisibility(View.VISIBLE);

                    final boolean mobileTrack = SPutils.getBoolean(getApplicationContext(), "MobileTrack", true);
                    setting_switch.setChecked(mobileTrack);

                    setting_switch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (mobileTrack) {
                               stopService(new Intent(getApplicationContext(),ListenCallService.class));
                                SPutils.putBoolean(getApplicationContext(),"MobileTrack",!mobileTrack);
                            }else{
                                startService(new Intent(getApplicationContext(),ListenCallService.class));
                                SPutils.putBoolean(getApplicationContext(),"MobileTrack",!mobileTrack);
                            }
                        }
                    });


                    break;

                case 2:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[2]);
                    setting_switch.setVisibility(View.VISIBLE);

                    final boolean homeAnim = SPutils.getBoolean(SettingActivity.this, "HomeAnim", true);
                    setting_switch.setChecked(homeAnim);

                    setting_switch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SPutils.putBoolean(SettingActivity.this,"HomeAnim",!homeAnim);
                            Intent intent = getIntent();
                            intent.putExtra("HomeAnim",!homeAnim);
                            setResult(2,intent);
                        }
                    });
                    break;
                case 3:
//                    setting_icons.setImageResource();
                    setting_content.setText(set_content[3]);
                    setting_switch.setVisibility(View.GONE);
                    break;
            }
            return view;
        }

    }

}
