package com.android.common.h5.client;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.common.R;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MyWebChromeClient extends WebChromeClient {

    private static final String TAG = "MyWebChromeClient";
    private OpenFileChooserCallBack mOpenFileChooserCallBack;
    private ProgressBar progressBar;
    private TextView tv_title;

    public MyWebChromeClient(View view) {
        progressBar = view.findViewById(R.id.progressbar);
        tv_title = view.findViewById(R.id.tv_title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        progressBar.setProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Log.d(TAG, "onReceivedTitle: "+title);
        tv_title.setText(title);
    }


    public void setWebViewClientInterface(OpenFileChooserCallBack mOpenFileChooserCallBack) {
        this.mOpenFileChooserCallBack = mOpenFileChooserCallBack;
    }


    // =========文件上传=============

    // For Android 5.0+
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        mOpenFileChooserCallBack.showFileChooserCallBack(filePathCallback);
        return true;
    }

    //For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);
    }


    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }


    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }


    public interface OpenFileChooserCallBack {
        // 5.0 以下
        void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);

        // 5.0 以上
        void showFileChooserCallBack(ValueCallback<Uri[]> filePathCallback);
    }

}
