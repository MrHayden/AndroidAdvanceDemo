package com.xxm.demohtmlanalysis;

import android.view.View;

import com.xxm.demohtmlanalysis.reptile.ChessCardActivity;
import com.xxm.demohtmlanalysis.reptile.LOLFixtureListActivity;
import com.xxm.demohtmlanalysis.reptile.LOLMsgActivity;

/**
 * Created by xxm on 2019/4/2 0002
 */
public class MainActivity extends BaseActivity {

    @Override
    public int initViewId() {
        return R.layout.activity_main;
    }


    public void onChessCard(View view) {
        goActivity(ChessCardActivity.class);
    }

    public void onLOLMsg(View view) {
        goActivity(LOLMsgActivity.class);
    }

    public void onLOLFixtureList(View view) {
        goActivity(LOLFixtureListActivity.class);
    }
}
