package com.xxm.demohtmlanalysis.reptile;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.xxm.demoglide.widget.WgShapeImageView;
import com.xxm.demohtmlanalysis.BaseActivity;
import com.xxm.demohtmlanalysis.R;
import com.xxm.demohtmlanalysis.WebActivity;
import com.xxm.demohtmlanalysis.bean.BeanLOLMsg;
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
 * LOL资讯
 */
public class LOLMsgActivity extends BaseActivity {

    private final String TAG = LOLMsgActivity.class.getSimpleName();

    private TwinklingRefreshLayout twinklingRefreshLayout;
    private ListView listView;

    private CommonAdapter<BeanLOLMsg> commonAdapter;
    private List<BeanLOLMsg> beanLOLMsgList;

    private int mPos = 1;

    //数据地址
    private final String DATA_URL = "http://www.cmegz.com";
    private BeanLOLMsg beanLOLMsg;
    private boolean isRefresh = true;

    @Override
    public int initViewId() {
        return R.layout.activity_list;
    }

    public void initView() {

        listView = findViewById(R.id.list_view);
        twinklingRefreshLayout = findViewById(R.id.refresh_layout);
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                twinklingRefreshLayout.setEnableLoadmore(true);
                beanLOLMsgList.clear();
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
        beanLOLMsgList = new ArrayList<>();
        addData(mPos);
        commonAdapter = new CommonAdapter<BeanLOLMsg>(this, R.layout.item_lol_msg_view, beanLOLMsgList) {
            @Override
            protected void convert(ViewHolder viewHolder, final BeanLOLMsg item, int position) {
                LinearLayout lin_item = viewHolder.getView(R.id.lin_item);
                WgShapeImageView wg_img = viewHolder.getView(R.id.wg_img);
                TextView tvTitle = viewHolder.getView(R.id.tv_title);
                TextView tvContent = viewHolder.getView(R.id.tv_content);
                TextView tvName = viewHolder.getView(R.id.tv_name);
                TextView tvTime = viewHolder.getView(R.id.tv_time);
                TextView tvGameName = viewHolder.getView(R.id.tv_game_name);
                wg_img.setUrl(item.getImgUrl());
                tvTitle.setText(item.getTitle());
                tvName.setText(item.getAuthor());
                tvContent.setText(item.getContent());
                tvTime.setText(item.getTimeStr());
                tvGameName.setText(item.getGameName());
                lin_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(item.getJumpUrl())) return;
                        Intent intent = new Intent(LOLMsgActivity.this, WebActivity.class);
                        intent.putExtra("url", item.getJumpUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        listView.setAdapter(commonAdapter);
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
                    loadUrl = DATA_URL+"/t/lol";
                } else {
                    loadUrl = DATA_URL + "/t/lol/index_" + pos+".html";
                }
                Connection connection = Jsoup.connect(loadUrl);
                try {
                    Document doc = connection.timeout(30000).get();

                    Element elementFirst = doc.getElementsByClass("topnews-content").first();
                    if (elementFirst != null) {
                        Elements elements = doc.getElementsByClass("media");
                        if (elements != null && elements.size() > 0) {
                            for (Element element : elements) {
                                beanLOLMsg = new BeanLOLMsg();
                                String jumpUrl = DATA_URL + element.getElementsByTag("a").first().attr("href");
                                //通过img标签的src属性获得
                                String imgUrl = element.getElementsByTag("img").first().attr("src");
                                beanLOLMsg.setJumpUrl(jumpUrl);
                                beanLOLMsg.setImgUrl(imgUrl);
                                //文字内容
                                Element element1 = element.getElementsByClass("media-body").first();
                                if (element1 != null) {
                                    String title = element1.getElementsByClass("media-body-title").first().getElementsByTag("a").first().text();
                                    String content = element1.getElementsByClass("media-body-content").first().getElementsByTag("a").first().text();

                                    beanLOLMsg.setTitle(title);
                                    beanLOLMsg.setContent(content);
                                    Element element2 = element1.getElementsByClass("media-body-meta").first();
                                    if (element2 != null) {
                                        String author = element2.getElementsByClass("author").first().text();
                                        String timeStr = element2.getElementsByClass("date").first().text();
                                        String gameName = element2.getElementsByClass("tag").first().getElementsByTag("a").first().text();
                                        beanLOLMsg.setAuthor(author);
                                        beanLOLMsg.setTimeStr(timeStr);
                                        beanLOLMsg.setGameName(gameName);
                                    }
                                }
                                beanLOLMsgList.add(beanLOLMsg);
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
