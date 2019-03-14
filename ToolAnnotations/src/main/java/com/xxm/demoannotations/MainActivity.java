package com.xxm.demoannotations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xxm.demoannotations.annotation.IBindViews;
import com.xxm.demoannotations.annotation.IBindViewsUtil;
import com.xxm.demoannotations.annotation.IOnClicks;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @IBindViews(R.id.iv_hello)
    private Button ivHello;

    @IBindViews(R.id.iv_hello2)
    private Button ivHello2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IBindViewsUtil.reflectIBindViews(this);
        IBindViewsUtil.reflectIOnClicks(this);

    }


    @IOnClicks({R.id.iv_hello, R.id.iv_hello2})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hello:
                ivHello.setText("通过注解获得了控件的id");
                break;
            case R.id.iv_hello2:
                ivHello2.setText("通过注解获得了控件的id2");
                break;
        }

    }
}
