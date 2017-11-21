package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dastudio.mobilesafe.R;

public class MainActivity extends AppCompatActivity {

    String[] funcNames = {"Antivirus","Apps","Process","Tools"};
    int[] imagePath = {R.mipmap.img_antivirus,R.mipmap.img_apps,R.mipmap.img_process,R.mipmap.img_tools};
    private ImageView mIv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >=21) {
            getSupportActionBar().setElevation(0);
        }

        mIv_logo = findViewById(R.id.iv_logo);
        mIv_logo.setImageResource(R.mipmap.img_logo);
        logoAnim();

        GridView gv_main = findViewById(R.id.gv_main);
        gv_main.setAdapter(new MyAdapter());
        gridOnClick(gv_main);

    }
    public void logoAnim(){
        AlphaAnimation aa = new AlphaAnimation(1.0f,0.0f);
        aa.setDuration(2000);
        aa.setRepeatCount(Animation.INFINITE);
        aa.setRepeatMode(Animation.REVERSE);
        mIv_logo.startAnimation(aa);
    }



    private void gridOnClick(GridView gv_main) {
        gv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Antivirus", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "Apps", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "Process", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent intent = new Intent(MainActivity.this, ToolsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"FIND");
        menu.add(0,2,0,"RATE");
        menu.add(0,3,0,"SETTING");
        menu.add(0,4,0,"ABOUT");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this,"Designing...",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"Designing...",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
            case 4:
                Intent intent1 = new Intent(this,AboutActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
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
            @SuppressLint("ViewHolder")
            View view = View.inflate(getApplicationContext(), R.layout.item_home, null);
            @SuppressLint("CutPasteId") ImageView iv_image = view.findViewById(R.id.iv_image);
            @SuppressLint("CutPasteId") TextView tv_functions = view.findViewById(R.id.tv_function);
            iv_image.setImageResource(imagePath[position]);
            tv_functions.setText(funcNames[position]);

            return view;
        }
    }
}
