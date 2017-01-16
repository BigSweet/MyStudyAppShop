package com.demo.swt.mystudyappshop.SelectCityActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demo.swt.mystudyappshop.R;

/**
 * Created by pc on 2017/1/4.
 */

public class SelectCityActivity extends FragmentActivity {
    private RadioGroup radioGroup;
    private RadioButton manRadio, womenRadio;
    private TextView mRadioTextMan,mRadioTextFeman;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        manRadio = (RadioButton) findViewById(R.id.man_radio);
        womenRadio = (RadioButton) findViewById(R.id.women_radio);
        mRadioTextMan= (TextView) findViewById(R.id.radio_text_man);
        mRadioTextFeman= (TextView) findViewById(R.id.radio_text_feman);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == manRadio.getId()) {
                    mRadioTextMan.setTextColor(Color.parseColor("#FF000000"));
                    mRadioTextFeman.setTextColor(Color.parseColor("#FF999999"));
                } else if (checkedId == womenRadio.getId()) {
                    mRadioTextFeman.setTextColor(Color.parseColor("#FF000000"));
                    mRadioTextMan.setTextColor(Color.parseColor("#FF999999"));
                }
            }
        });
    }
}
