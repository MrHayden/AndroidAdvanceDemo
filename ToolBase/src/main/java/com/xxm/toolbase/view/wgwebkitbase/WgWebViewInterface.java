package com.xxm.toolbase.view.wgwebkitbase;

/**
 * Created by shuai on 2015/8/10.
 */
public interface WgWebViewInterface {
    void onPageStart();

    void onPageFinish();

    void onProgressChanged(int newProgress);

    void receiveTitle(String title);

    boolean onUrlLink(String url);

    void onError(String url);
}
