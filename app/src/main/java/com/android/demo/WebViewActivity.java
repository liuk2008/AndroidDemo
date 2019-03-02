package com.android.demo;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import com.android.common.base.activity.CoreActivity;
import com.android.common.h5.WebViewHelper;
import com.android.common.h5.client.MyWebViewClient;
import com.android.common.h5.client.WebViewUtils;
import com.android.utils.common.LogUtils;

public class WebViewActivity extends CoreActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configStatusBar(R.drawable.shape_titlebar);
        webViewHelper = WebViewHelper.create(this);
        setContentView(webViewHelper.getRootView());

        WebViewUtils.setCookie("token", "1F99DF52AFFD474CB5C5331C86AFB4CA.10D5D0054A8C144C38693F714694BF2C");
        WebViewUtils.setCookie("currentUserId", "ED08E7A6C6F7459FA264C736482CD5F6");
        WebViewUtils.setCookie("origin", "android");
        WebViewUtils.setCookie("platform", "finance");
        WebViewUtils.setCookie("version", "1.3.0.0");
        WebViewUtils.setCookie("channel", "official");
        WebViewUtils.setCookie("phone", "18909131172");

        webViewHelper.setWebViewClientInterface(new MyWebViewClient.WebViewClientInterface() {
            @Override
            public boolean onLoadUrl(WebView webView, String url) {
                LogUtils.logd(TAG, "onLoadUrl:" + url);
                Uri uri = Uri.parse(url);
                String host = uri.getHost();
                if ("returnToSpecifiedPage".equals(host)) {
                    finish();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void executorJs(WebView webView, String url) {
            }
        });

        /*
         * 为WebView添加可供JS调用的本地方法。WebView.addJavascriptInterface(Object object, String name)
         * object参数的实现是一个普通的JavaClass,name参数会被绑定成为js中window对象的一个属性，该属性指向object参数,
         * 因此js中可以通过window.name.methodName()调用object对象有的本地方法。
         * window.CallAppFunction.onReceiveTitleInfo
         */
        webViewHelper.addJavascriptInterface(null,"");

 //       webView.loadUrl("https://appapp.snxw.com/sy/tj_3696/201809/t20180930_376601.html");
//        webView.loadUrl("http://jfx.qdfaw.com:6180/truck-qingdao-web/");
        webViewHelper.load("https://h5.lawcert.com/trade/withdraw");

    }


}
