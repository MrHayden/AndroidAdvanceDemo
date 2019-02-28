package com.xxm.toolbase.view.wgwebkitbase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xxm.toolbase.R;
import com.xxm.toolbase.view.dlg.DlgSys;

/**
 * Created by shuai on 2015/8/7.
 */
public class WgChromeClientBase extends WebChromeClient {
    private WgWebViewInterface mWebViewInterface;
    private Activity mContext;
    private View mCustomView;
    private FrameLayout mFullscreenContainer;
    private Bitmap xdefaltvideo;
    private View xprogressvideo;
    CustomViewCallback mCustomViewCallback;

    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

    public WgChromeClientBase(Activity context, WgWebViewInterface webViewInterface) {
        mContext = context;
        mWebViewInterface = webViewInterface;
    }

    // This is an undocumented method, it _is_ used, whatever Eclipse
    // may think :)
    // Used to show a file chooser dialog.

    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        DlgSys.show(mContext, mContext.getString(R.string.Commons_JavaScriptDialog), message, mContext.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        });
        return true;
    }

    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        DlgSys.show(mContext, mContext.getString(R.string.Commons_JavaScriptDialog), message, mContext.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.confirm();
            }
        }, mContext.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                result.cancel();
            }
        });
        return true;
    }

    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {

        final LayoutInflater factory = LayoutInflater.from(mContext);
        final View v = factory.inflate(R.layout.javascript_prompt_dialog, null);
        ((TextView) v.findViewById(R.id.JavaScriptPromptMessage))
                .setText(message);
        ((EditText) v.findViewById(R.id.JavaScriptPromptInput))
                .setText(defaultValue);
        DlgSys.show(mContext, mContext.getString(R.string.Commons_JavaScriptDialog), message, v, mContext.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        String value = ((EditText) v
                                .findViewById(R.id.JavaScriptPromptInput))
                                .getText().toString();
                        result.confirm(value);
                    }
                }, mContext.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        result.cancel();
                    }
                });
        return true;

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (view instanceof WgWebViewBase)
            ((WgWebViewBase) view).setProgress(newProgress);
        mWebViewInterface.onProgressChanged(newProgress);
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        FrameLayout decor = (FrameLayout) mContext.getWindow().getDecorView();
        mFullscreenContainer = new FullscreenHolder(mContext);
        mFullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(mFullscreenContainer, COVER_SCREEN_PARAMS);
        mCustomView = view;
        setStatusBarVisibility(false);
        mCustomViewCallback = callback;
    }

    @Override
    public void onHideCustomView() {
        if (mCustomView == null)
            return;
        mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FrameLayout decor = (FrameLayout) mContext.getWindow().getDecorView();
        decor.removeView(mFullscreenContainer);
        mFullscreenContainer = null;
        mCustomView = null;
        setStatusBarVisibility(true);
        mCustomViewCallback.onCustomViewHidden();
    }

    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(@NonNull MotionEvent evt) {
            return true;
        }

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        mWebViewInterface.receiveTitle(title);
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mContext.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//    @Override
//    public Bitmap getDefaultVideoPoster() {
//        // Log.i(LOGTAG, "here in on getDefaultVideoPoster");
//        if (xdefaltvideo == null && mContext != null) {
////            xdefaltvideo = BitmapFactory.decodeResource(mContext.getResources(),
////                    R.drawable.bg_news_img_default);
//        }
//        return xdefaltvideo;
//    }
//
//    // 视频加载时进程loading
//    @Override
//    public View getVideoLoadingProgressView() {
//        // Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
//
////        if (xprogressvideo == null) {
////            LayoutInflater inflater = LayoutInflater.from(mContext);
////            xprogressvideo = inflater.inflate(R.layout.dlg_process_bar, null);
////        }
//        return xprogressvideo;
//    }

}
