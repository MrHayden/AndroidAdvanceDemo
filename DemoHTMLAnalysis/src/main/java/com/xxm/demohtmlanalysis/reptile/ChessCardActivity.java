package com.xxm.demohtmlanalysis.reptile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.xxm.demoglide.widget.WgShapeImageView;
import com.xxm.demohtmlanalysis.R;
import com.xxm.demohtmlanalysis.WebActivity;
import com.xxm.demohtmlanalysis.bean.BeanChessCard;
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
 * 棋牌数据
 */
public class ChessCardActivity extends AppCompatActivity {

    private final String TAG = ChessCardActivity.class.getSimpleName();

    private TwinklingRefreshLayout twinklingRefreshLayout;
    private ListView listView;

    private CommonAdapter<BeanChessCard> commonAdapter;
    private List<BeanChessCard> beanInformationList;

    private int mPos = 1;

    //数据地址
    private final String DATA_URL = "http://www.gametea.com";
    private BeanChessCard beanInformation;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.list_view);
        twinklingRefreshLayout = findViewById(R.id.refresh_layout);

        initView();
        initData();
    }

    protected void initView() {
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                twinklingRefreshLayout.setEnableLoadmore(true);
                beanInformationList.clear();
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

    protected void initData() {
        beanInformationList = new ArrayList<>();
        addData(mPos);
        commonAdapter = new CommonAdapter<BeanChessCard>(this, R.layout.item_view, beanInformationList) {
            @Override
            protected void convert(ViewHolder viewHolder, final BeanChessCard item, int position) {
                LinearLayout lin_item = viewHolder.getView(R.id.lin_item);
                WgShapeImageView wg_img = viewHolder.getView(R.id.wg_img);
                TextView tvName = viewHolder.getView(R.id.tv_name);
                TextView tvContent = viewHolder.getView(R.id.tv_content);
                wg_img.setUrl(item.getThum_url());
                tvName.setText(item.getName());
                tvContent.setText(item.getContent());
                lin_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ChessCardActivity.this, WebActivity.class);
                        intent.putExtra("url", item.getUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        listView.setAdapter(commonAdapter);
    }

    /**
     * 网上抓取数据，html解析
     * @param pos
     */
    private void addData(final int pos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loadUrl = DATA_URL + "/article/20?page=" + pos;
                Connection connection = Jsoup.connect(loadUrl);
                try {
                    Document doc = connection.timeout(30000).get();
                    Elements elements = doc.getElementsByClass("newsWrap");
                    if (elements != null && elements.size() > 0) {
                        for (Element element : elements) {
                            //通过img标签的src属性获得
                            String imgUrl = "http:" + element.getElementsByTag("img").first().attr("src");
                            String name = element.getElementsByClass("gamelistimg").first().getElementsByTag("a").first().text();
                            String content = element.getElementsByClass("gamelistname").first().text();
                            String url = DATA_URL + element.getElementsByClass("btnViewNews").first().getElementsByTag("a").first().attr("href");
                            beanInformation = new BeanChessCard();
                            beanInformation.setContent(content);
                            beanInformation.setName(name);
                            beanInformation.setThum_url(imgUrl);
                            beanInformation.setUrl(url);
                            beanInformationList.add(beanInformation);
                        }
                        handler.sendEmptyMessage(1);
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
