package com.xxm.democustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xxm.democustomview.practice.MySurfaceView;

/**
 * Created by xxm on 2019/3/22 0022
 */
public class MySurfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MySurfaceView(this));
    }
}
