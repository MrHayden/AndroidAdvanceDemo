/*
 * Zirco Browser for Android
 *
 * Copyright (C) 2010 - 2012 J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.xxm.toolbase.view.wgwebkitbase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.xxm.toolbase.R;
import com.xxm.toolbase.view.dlg.DlgSys;

import java.util.List;


/**
 * Convenient extension of WebViewClient.
 */
public class WgWebViewClientBase extends WebViewClient {
    private String mUrl = "";
    private Context mContext;
    private WgWebViewInterface mWgWebViewInterface;

    public WgWebViewClientBase(Context context, WgWebViewInterface wgWebViewInterface) {
        super();
        mContext = context;
        mWgWebViewInterface = wgWebViewInterface;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (view instanceof WgWebViewBase)
            ((WgWebViewBase) view).notifyPageFinished();
        mUrl = url;
        mWgWebViewInterface.onPageFinish();
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (view instanceof WgWebViewBase)
            ((WgWebViewBase) view).notifyPageStarted();
        mWgWebViewInterface.onPageStart();
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        mWgWebViewInterface.onError(failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView view,
                                   @NonNull final SslErrorHandler handler, SslError error) {

        StringBuilder sb = new StringBuilder();

        sb.append(view.getResources().getString(
                R.string.Commons_SslWarningsHeader));
        sb.append("\n\n");

        if (error.hasError(SslError.SSL_UNTRUSTED)) {
            sb.append(" - ");
            sb.append(view.getResources().getString(
                    R.string.Commons_SslUntrusted));
            sb.append("\n");
        }

        if (error.hasError(SslError.SSL_IDMISMATCH)) {
            sb.append(" - ");
            sb.append(view.getResources().getString(
                    R.string.Commons_SslIDMismatch));
            sb.append("\n");
        }

        if (error.hasError(SslError.SSL_EXPIRED)) {
            sb.append(" - ");
            sb.append(view.getResources()
                    .getString(R.string.Commons_SslExpired));
            sb.append("\n");
        }

        if (error.hasError(SslError.SSL_NOTYETVALID)) {
            sb.append(" - ");
            sb.append(view.getResources().getString(
                    R.string.Commons_SslNotYetValid));
            sb.append("\n");
        }
        DlgSys.show(view.getContext(),
                view.getResources().getString(R.string.Commons_SslWarning),
                sb.toString(),
                view.getResources().getString(R.string.Commons_Continue),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        handler.proceed();
                    }

                }, view.getResources().getString(R.string.Commons_Cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        handler.cancel();
                    }
                });
//        handler.proceed();  // 接受所有网站的证书
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            Log.i("url", request.getUrl().toString());
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("url", url);
        skipScheme(mContext,url);
        if (mWgWebViewInterface != null) {
            if (mWgWebViewInterface.onUrlLink(url)) {
                return true;
            }
        }
        if (isExternalApplicationUrl(url)) {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(i);

            } catch (Exception e) {
                // Notify user that the vnd url cannot be viewed.
                DlgSys.show(mContext,
                        getStringById(R.string.Main_VndErrorTitle),
                        getStringById(R.string.Main_VndErrorMessage),
                        getStringById(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
            }
            return true;
        } else {
            // If the url is not from GWT mobile view, and is in the mobile view
            // url list, then load it with GWT.
            if (view instanceof WgWebViewBase)
                ((WgWebViewBase) view).resetLoadedUrl();
            return false;
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(final WebView view,
                                          @NonNull final HttpAuthHandler handler, final String host,
                                          final String realm) {
        String username = null;
        String password = null;

        boolean reuseHttpAuthUsernamePassword = handler
                .useHttpAuthUsernamePassword();

        if (reuseHttpAuthUsernamePassword && view != null) {
            String[] credentials = view
                    .getHttpAuthUsernamePassword(host, realm);
            if (credentials != null && credentials.length == 2) {
                username = credentials[0];
                password = credentials[1];
            }
        }

        if (username != null && password != null) {
            handler.proceed(username, password);
        } else {
            LayoutInflater factory = LayoutInflater.from(mContext);
            final View v = factory.inflate(R.layout.http_authentication_dialog, null);

            if (username != null) {
                ((EditText) v.findViewById(R.id.username_edit))
                        .setText(username);
            }
            if (password != null) {
                ((EditText) v.findViewById(R.id.password_edit))
                        .setText(password);
            }

            DlgSys.show(
                    mContext,
                    getStringById(R.string.HttpAuthenticationDialog_DialogTitle),
                    null,
                    v,
                    getStringById(R.string.Commons_Proceed),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            String nm = ((EditText) v
                                    .findViewById(R.id.username_edit))
                                    .getText().toString();
                            String pw = ((EditText) v
                                    .findViewById(R.id.password_edit))
                                    .getText().toString();
                            if (view != null) {
                                view.setHttpAuthUsernamePassword(host, realm,
                                        nm, pw);
                            }
                            handler.proceed(nm, pw);
                        }
                    }, getStringById(R.string.Commons_Cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.cancel();
                        }
                    });
            v.findViewById(R.id.username_edit).requestFocus();
        }
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    private boolean isExternalApplicationUrl(String url) {
        return url.startsWith("vnd.") || url.startsWith("rtsp://")
                || url.startsWith("itms://") || url.startsWith("itpc://") || url.contains("isGoExternalBrowser=1") || url.startsWith("weixin://wap/pay?");
    }

    private String getStringById(int id) {
        return mContext.getString(id);
    }

    /**
     * 跳转到支付宝
     * @param context
     * @param newurl
     * @return
     */
    private  boolean skipScheme(Context context, String newurl){
        if(TextUtils.isEmpty(newurl) || !newurl.contains("scheme")){
            return false;
        }
        Log.e("webview_scheme","skipScheme处理自定义scheme-->" + newurl);
        try {
            // 以下固定写法
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newurl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            List<ResolveInfo> resolves = context.getPackageManager().queryIntentActivities(intent,0);
            if(resolves.size()>0){
                ((Activity)context).startActivityIfNeeded(intent, -1);
            }
        } catch (Exception e) {
            // 防止没有安装的情况
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
