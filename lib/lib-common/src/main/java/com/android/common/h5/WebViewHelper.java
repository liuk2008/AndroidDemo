package com.android.common.h5;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.common.R;
import com.android.common.h5.client.MyWebChromeClient;
import com.android.common.h5.client.MyWebViewClient;
import com.android.common.h5.client.WebViewUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebViewHelper {

    private static final String TAG = WebViewHelper.class.getSimpleName();

    private MyWebChromeClient webChromeClient;
    private MyWebViewClient webViewClient;

    private FragmentActivity activity;
    private MyWebView mWebView;
    private ImageView iv_arrow, iv_close;
    private View rootView;


    public static WebViewHelper create(FragmentActivity activity) {
        WebViewHelper webViewHelper = new WebViewHelper(activity);
        webViewHelper.initView();
        webViewHelper.initListener();
        webViewHelper.initClient();
        webViewHelper.registerLifecycle();
        return webViewHelper;
    }

    private WebViewHelper(FragmentActivity activity) {
        this.activity = activity;
    }

    private void initView() {
        View rootView = LayoutInflater.from(activity).inflate(R.layout.activity_h5, null);
        mWebView = rootView.findViewById(R.id.webview);
        iv_arrow = rootView.findViewById(R.id.iv_arrow);
        iv_close = rootView.findViewById(R.id.iv_close);
    }


    private void initListener() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    activity.finish();
                }
            }
        });
        // 处理webview返回键
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initClient() {
        webViewClient = new MyWebViewClient(rootView);
        mWebView.setWebViewClient(webViewClient);
        webChromeClient = new MyWebChromeClient(rootView);
        mWebView.setWebChromeClient(webChromeClient);
    }

    private void registerLifecycle() {
        activity.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            private void onDestroy() {
                WebViewHelper.this.onDestroy();
            }
        });
    }

    public void setWebViewClientInterface(MyWebViewClient.WebViewClientInterface clientInterface) {
        webViewClient.setWebViewClientInterface(clientInterface);
    }

    public void setWebChromeClientInterface(MyWebChromeClient.OpenFileChooserCallBack clientInterface) {
        webChromeClient.setWebViewClientInterface(null);
    }

    public View getRootView() {
        return rootView;
    }

    // 释放资源
    private void onDestroy() {
        iv_arrow.setOnClickListener(null);
        iv_close.setOnClickListener(null);
        mWebView.setOnKeyListener(null);
        WebViewUtils.clearHeader();
        WebViewUtils.clearCookie();
        if (mWebView != null) {
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }
            mWebView.stopLoading();
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
            activity = null;
        }
    }


    // 从assets中加载文件
    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(
                    activity.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 调用js方法
    public void excJsMethod(WebView webView, String method) {
        if (method.endsWith("()")) {
            webView.loadUrl("javascript:" + method);
        } else {
            webView.loadUrl("javascript:" + method + "()");
        }
    }

    // 注入本地js脚本
    public void injectJs(WebView webView, String src) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(src, null);
        } else {
            webView.loadUrl(String.format("javascript:%s", src));
        }
    }

    /**
     * 本地调用js函数
     * js 函数返回值在onReceiveValue回调函数中取得，
     * evaluateJavascript方法必须在UI线程（主线程）调用，因此onReceiveValue也执行在主线程。
     */
    public void evaluateJs(WebView webView, String method, ValueCallback<String> callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (callback == null) return;
            webView.evaluateJavascript(method, callback);
        } else {
            // 4.4 以下需要手动执行js方法，js函数的返回值要再通过js调用本地java方法传递
            webView.loadUrl(method);
        }
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object object, String method) {
        mWebView.addJavascriptInterface(object, method);
    }

    public void load(String url) {
        mWebView.loadUrl(url);
    }

}
