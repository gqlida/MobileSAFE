package com.dastudio.mobilesafe.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dastudio.mobilesafe.R;

public class ToolsActivity extends AppCompatActivity {

    private android.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        mActionBar = getActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.tools_name);

    }

    class MyAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return 6;
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

                return view;
            }
        }


}
