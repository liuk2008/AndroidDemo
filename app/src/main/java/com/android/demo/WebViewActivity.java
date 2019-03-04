package com.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.android.common.h5.WebViewHelper;
import com.android.common.h5.client.MyWebChromeClient;
import com.android.common.h5.client.MyWebViewClient;
import com.android.common.h5.client.WebViewUtils;
import com.android.utils.common.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewHelper = WebViewHelper.create(this);
        setContentView(webViewHelper.getRootView());

        // 设置cookie
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
                return false;
            }

            @Override
            public void executorJs(WebView webView, String url) {
                LogUtils.logd(TAG, "executorJs: ");
            }
        });

        webViewHelper.setWebChromeClientInterface(new MyWebChromeClient.OpenFileChooserCallBack() {
            // ===============点击表单选择图片或者拍照==============
            @Override
            public void showPicWindow() {
                webViewHelper.showPicWindow();
            }
        });

        String url = "http://jfx.qdfaw.com:6180/truck-qingdao-web/";
        String token = "00bdd0c39498430e94801beb6578ef6dg";
        String appType = "2", userType = "1", platform = "2";
        try {
            token = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = String.format("%s?token=%s&appType=%s&userType=%s&platform=%s&cityNmae=%s", url,
                token, appType, userType, platform, "西安");

        webViewHelper.load(url);
//       webView.loadUrl("https://appapp.snxw.com/sy/tj_3696/201809/t20180930_376601.html");
//       webViewHelper.load("https://h5.lawcert.com/trade/withdraw");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webViewHelper.setPicData(requestCode, resultCode, data);
    }
    
}
