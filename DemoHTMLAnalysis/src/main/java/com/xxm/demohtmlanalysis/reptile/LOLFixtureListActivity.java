package com.xxm.demohtmlanalysis.reptile;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.xxm.demoglide.widget.WgShapeImageView;
import com.xxm.demohtmlanalysis.BaseActivity;
import com.xxm.demohtmlanalysis.R;
import com.xxm.demohtmlanalysis.WebActivity;
import com.xxm.demohtmlanalysis.bean.BeanFixture;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxm on 2019/4/2 0002
 * LOL赛程表
 */
public class LOLFixtureListActivity extends BaseActivity {

    private final String TAG = LOLFixtureListActivity.class.getSimpleName();

    private TwinklingRefreshLayout twinklingRefreshLayout;
    private GridView gridView;

    private CommonAdapter<BeanFixture> commonAdapter;
    private List<BeanFixture> beanFixtureList;

    private int mPos = 1;

    //数据地址
    private final String DATA_URL = "http://1zplay.com";
    private BeanFixture beanFixture;
    private boolean isRefresh = true;

    @Override
    public int initViewId() {
        return R.layout.activity_grid;
    }

    public void initView() {

        gridView = findViewById(R.id.grid_view);
        twinklingRefreshLayout = findViewById(R.id.refresh_layout);
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                twinklingRefreshLayout.setEnableLoadmore(true);
                beanFixtureList.clear();
                mPos = 1;
                isRefresh = true;
                addData(mPos);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                mPos++;
                isRefresh = false;
                addData(mPos);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (isRefresh) {
                        twinklingRefreshLayout.finishRefreshing();
                    } else {
                        twinklingRefreshLayout.finishLoadmore();
                    }
                    commonAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public void initData() {
        beanFixtureList = new ArrayList<>();
        addData(mPos);
        commonAdapter = new CommonAdapter<BeanFixture>(this, R.layout.item_lol_fixture_view, beanFixtureList) {
            @Override
            protected void convert(ViewHolder viewHolder, final BeanFixture item, int position) {
                RelativeLayout lin_item = viewHolder.getView(R.id.re_item);
                WgShapeImageView wg_img = viewHolder.getView(R.id.wg_img);
                WgShapeImageView iv_state = viewHolder.getView(R.id.iv_state);
                WgShapeImageView iv_game_icon = viewHolder.getView(R.id.iv_game_icon);
                TextView tv_star_num = viewHolder.getView(R.id.tv_star_num);
                TextView tv_game_name = viewHolder.getView(R.id.tv_game_name);
                wg_img.setUrl(item.getBgUrl());
                iv_state.setUrl(item.getStateUrl());
                iv_game_icon.setUrl(item.getGameIconUrl());
                tv_star_num.setText(String.valueOf(item.getStarNum())+"颗星");
                tv_game_name.setText(item.getTitle());
                lin_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(item.getJumpUrl())) return;
                        Intent intent = new Intent(LOLFixtureListActivity.this, WebActivity.class);
                        intent.putExtra("url", item.getJumpUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        gridView.setAdapter(commonAdapter);
    }

    /**
     * 网上抓取数据，html解析
     *
     * @param pos
     */
    private void addData(final int pos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loadUrl;
                if (pos == 1) {
                    loadUrl = DATA_URL + "/league";
                } else {
                    loadUrl = DATA_URL + "/league?category=all&page=" + pos;
                }
                Connection connection = Jsoup.connect(loadUrl);
                try {
                    Document doc = connection.timeout(30000).get();

                    Element elementFirst = doc.getElementsByClass("content-1zplay").first();
                    if (elementFirst != null) {
                        Elements elements = doc.getElementsByClass("col-xs-3");
                        if (elements != null && elements.size() > 0) {
                            for (Element element : elements) {
                                beanFixture = new BeanFixture();
                                String jumpUrl = DATA_URL + element.getElementsByTag("a").first().attr("href");
                                //通过img标签的src属性获得
                                String bgUrl = element.getElementsByClass("league-image").first().attr("src");
                                String stateUrl = element.getElementsByClass("league-state").first().attr("src");
                                String iconUrl = element.getElementsByClass("league-icon").first().attr("src");
                                Elements starElms = element.getElementsByClass("text-center league-stars").first().getElementsByClass("glyphicon glyphicon-star league-star");
                                int starNum = starElms == null ? 0 : starElms.size();
                                String gameName = element.getElementsByClass("league-title").first().text();
                                beanFixture.setJumpUrl(jumpUrl);
                                beanFixture.setStateUrl(stateUrl);
                                beanFixture.setBgUrl(bgUrl);
                                beanFixture.setGameIconUrl(iconUrl);
                                beanFixture.setStarNum(starNum);
                                beanFixture.setTitle(gameName);
                                beanFixtureList.add(beanFixture);
                            }
                            handler.sendEmptyMessage(1);
                        } else {
                            twinklingRefreshLayout.setEnableLoadmore(false);
                            Log.e(TAG, "----没有数据--");
                        }
                    } else {
                        twinklingRefreshLayout.setEnableLoadmore(false);
                        Log.e(TAG, "----没有数据--");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
