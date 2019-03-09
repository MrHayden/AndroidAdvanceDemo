package com.xxm.demolivedatabus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xxm.toolbase.base.BaseActivity;
import com.xxm.toolbase.bus.LiveDataBus;
import com.xxm.toolbase.bus.LiveDataBusUtil;
import com.xxm.toolbase.entity.LiveDataBusBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xxm on 2019/2/26 0026
 */
public class TwoActivity extends BaseActivity {

    @BindView(R.id.tv_textview)
    TextView tvMsg;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.a_two;
    }

    @Override
    public void initData() {
        super.initData();
        observerLiveDataBus(new String[]{"key_2","key_5"});
    }

    @Override
    public void handLiveDataBusBean(LiveDataBusBean rxBusBean) {
        super.handLiveDataBusBean(rxBusBean);
        switch (rxBusBean.getKeyTag()) {
            case "key_2":
            case "key_5":
                tvMsg.setText("收到消息了:" + rxBusBean.getValueTag());
                System.out.println("----111---" + rxBusBean.getValueTag());
                Toast.makeText(TwoActivity.this, rxBusBean.getKeyTag() + "收到消息了", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick({R.id.button_1, R.id.button_2})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_1:
                LiveDataBusUtil.getInstance().setLiveDataBusBeanValue("key_5", "本类发的消息");
                break;
            case R.id.button_2:
                LiveDataBusUtil.getInstance().setLiveDataBusBeanValue("key_4", "第二个类发的消息");
                break;
        }
    }

}
