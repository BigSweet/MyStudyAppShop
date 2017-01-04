package com.demo.swt.mystudyappshop.Fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.demo.swt.mystudyappshop.R;
import com.demo.swt.mystudyappshop.SelectCityActivity.SelectCityActivity;

/**
 * Created by pc on 2016/11/29.
 */

public class CateGoryFragment extends Fragment {
    private Button city;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category, container, false);
        view.findViewById(R.id.select_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
