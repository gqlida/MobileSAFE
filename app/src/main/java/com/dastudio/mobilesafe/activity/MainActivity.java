package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >=21) {
            getSupportActionBar().setElevation(0);

        }
        GridView gv_main = findViewById(R.id.gv_main);
        gv_main.setAdapter(new MyAdapter());
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
                        Toast.makeText(MainActivity.this, "Tools", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"You");
        menu.add(0,2,0,"Setting");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this,"退", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"进",Toast.LENGTH_SHORT).show();
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
            View view = View.inflate(getApplicationContext(), R.layout.item, null);
            @SuppressLint("CutPasteId") ImageView iv_image = view.findViewById(R.id.iv_image);
            @SuppressLint("CutPasteId") TextView tv_functions = view.findViewById(R.id.tv_function);
            iv_image.setImageResource(imagePath[position]);
            tv_functions.setText(funcNames[position]);

            return view;
        }
    }
}
