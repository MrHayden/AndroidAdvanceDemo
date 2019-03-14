package com.xxm.demolivedatabus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xxm.demoglide.widget.WgShapeImageView;
import com.xxm.toolbase.base.BaseActivity;
import com.xxm.toolbase.bus.LiveDataBusUtil;
import com.xxm.toolbase.entity.LiveDataBusBean;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_textview)
    TextView tvMsg;
    private int i, j, k = 0;

    @BindView(R.id.wg_img)
    WgShapeImageView imageView;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        observerLiveDataBus(new String[]{"key_1","key_3","key_4"});

        String url = "https://res.xfkjd.cn/wwj.png";
        imageView.setUrl(url);

    }

    @OnClick({R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                goToActivity(TwoActivity.class);
                break;
            case R.id.button_2:
                i++;
                LiveDataBusUtil.getInstance().setLiveDataBusBeanValue("key_1","测试消息" + i);
                break;
            case R.id.button_3:
                j++;
                LiveDataBusUtil.getInstance().setLiveDataBusBeanValue("key_2","key_2_消息" + j);
                break;
            case R.id.button_4:
                k++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LiveDataBusUtil.getInstance().postLiveDataBusBeanValue("key_3","key_3_消息" + k);
                    }
                }).start();
                break;
        }
    }

    @Override
    public void handLiveDataBusBean(LiveDataBusBean liveDataBusBean) {
        super.handLiveDataBusBean(liveDataBusBean);
        switch (liveDataBusBean.getKeyTag()){
            case "key_1":
            case "key_3":
            case "key_4":
                tvMsg.setText("收到消息了:" + liveDataBusBean.getValueTag());
                Toast.makeText(mContext, liveDataBusBean.getKeyTag() + "收到消息了"+liveDataBusBean.getValueTag(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

}
