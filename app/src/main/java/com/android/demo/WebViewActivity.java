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

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    private WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewHelper = WebViewHelper.create(this);
        setContentView(webViewHelper.getRootView());

//        testCookie();
        testFile();
//        testImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webViewHelper.setPicData(requestCode, resultCode, data);
    }

    private void testCookie() {
        // 设置cookie
        WebViewUtils.setCookie("token", "84643485AD2A48CD97F718421F9BAD32.08EA217B8E0792B04FB7090685B64BE6");
        WebViewUtils.setCookie("currentUserId", "ED08E7A6C6F7459FA264C736482CD5F6");
        WebViewUtils.setCookie("origin", "android");
        WebViewUtils.setCookie("platform", "finance");
        WebViewUtils.setCookie("version", "1.3.0.0");
        WebViewUtils.setCookie("channel", "official");
        WebViewUtils.setCookie("phone", "18909131172");
        webViewHelper.load("https://h5.lawcert.com/trade/withdraw");
    }

    private void testFile() {
        webViewHelper.setWebChromeClientInterface(new MyWebChromeClient.OpenFileChooserCallBack() {
            // ===============点击表单选择图片或者拍照==============
            @Override
            public void showPicWindow() {
                webViewHelper.showPicWindow();
            }
        });
        webViewHelper.load("http://jfx.qdfaw.com:6180/truck-qingdao-web/");
    }

    private void testImage() {
        webViewHelper.setWebViewClientInterface(new MyWebViewClient.WebViewClientInterface() {
            @Override
            public boolean onLoadUrl(WebView webView, String url) {
                LogUtils.logd(TAG, "onLoadUrl:" + url);
                return false;
            }
            @Override
            public void executorJs(WebView webView, String url) {
                LogUtils.logd(TAG, "executorJs: ");
                webViewHelper.showImage(webView);
            }
        });
        webViewHelper.load("file:///android_asset/test.html");
    }


}
