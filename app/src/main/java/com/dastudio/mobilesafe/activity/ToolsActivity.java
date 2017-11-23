package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;

public class ToolsActivity extends AppCompatActivity {

    int[] tools = {
            R.string.tools_01,
            R.string.tools_02,
            R.string.tools_03,
            R.string.tools_04,
            R.string.tools_05};

    private ActionBar mActionBar;
    private GridView mGv_tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.tools_name);


        mGv_tools = findViewById(R.id.gv_tools);
        mGv_tools.setAdapter(new MyAdapter());

        mGv_tools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(ToolsActivity.this, mobileTrackActivity.class);
                        startActivity(intent);
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
                return 5;
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
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                @SuppressLint("ViewHolder")
                View view = View.inflate(getApplicationContext(),R.layout.item_tools,null);

                TextView tv_function = view.findViewById(R.id.tv_function);
                tv_function.setText(tools[i]);

                return view;
            }
        }


}
