package com.android.demo;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.android.common.widget.webview.WebViewHelper;
import com.android.common.widget.webview.client.MyWebChromeClient;
import com.android.common.widget.webview.client.MyWebViewClient;
import com.android.common.widget.webview.client.WebViewUtils;
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
        webViewHelper.initClient();
        testToken();
//        testCookie();
//        testFile();
//        testImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webViewHelper.setPicData(requestCode, resultCode, data);
    }

    private void testCookie() {
        // 设置cookie和header
        WebViewUtils.setCookie("token", "F586EC06EAC944448260B0385DE96EFA.67D7F89876AA4446E2CF51C9136BADD8");
        WebViewUtils.setCookie("origin", "android");
        WebViewUtils.setCookie("platform", "finance");
        WebViewUtils.setHeader("channel", "official");
        WebViewUtils.setHeader("version","1.3.0.0");
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
        String url = "http://jfx.qdfaw.com:6180/truck-qingdao-web/";
        String token = "b2f65c72c3904d5c95c0ffc55c9f5a9e";
        try {
            token = URLEncoder.encode(token, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = String.format("%s?token=%s&appType=%s&userType=%s&platform=%s&cityNmae=%s", url,
                token, "2", "1", "2", "西安");
        webViewHelper.load(url);
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

            @Override
            public void SSLException(WebView view, SslErrorHandler handler, SslError error) {

            }

        });
        webViewHelper.load("file:///android_asset/test.html");
    }

    private void testToken() {
        webViewHelper.setWebViewClientInterface(new MyWebViewClient.WebViewClientInterface() {
            @Override
            public boolean onLoadUrl(WebView webView, String url) {
                LogUtils.logd(TAG, "onLoadUrl:" + url);
                WebViewUtils.setToken(url);
                return false;
            }

            @Override
            public void executorJs(WebView webView, String url) {
                LogUtils.logd(TAG, "executorJs: ");
            }

            @Override
            public void SSLException(WebView view, SslErrorHandler handler, SslError error) {

            }

        });
        webViewHelper.load("https://jrwx.lawcert.com/index");
    }


}
