package com.dastudio.mobilesafe.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dastudio.mobilesafe.R;
import com.dastudio.mobilesafe.utils.AddressDbUtils;

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


        mTrack_et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = editable.toString();
                if (!TextUtils.isEmpty(number)) {
                    String address = AddressDbUtils.getAddress(getApplicationContext(), number);
                    mTrack_tv_province.setText(address);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void find(View v){

        String number = mTrack_et_input.getText().toString().trim();

        String address = AddressDbUtils.getAddress(getApplicationContext(), number);

        mTrack_tv_province.setText(address);


    }
}
