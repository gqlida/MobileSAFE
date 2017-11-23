package com.dastudio.mobilesafe.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;

public class mobileTrackActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private EditText mTrack_et_input;
    private TextView mTrack_tv_province;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_track);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.mobileTrack_name);

        mTrack_et_input = findViewById(R.id.track_et_input);
        mTrack_tv_province = findViewById(R.id.track_tv_province);



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void find(View v){
        String input_text = mTrack_et_input.getText().toString().trim();
        if (input_text.startsWith("010")){
            mTrack_tv_province.setText("固话");
        }



    }
}
