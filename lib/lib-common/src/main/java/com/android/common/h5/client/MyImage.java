package com.android.common.h5.client;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.android.common.h5.ImageActivity;

public class MyImage {
    private FragmentActivity activity;

    public MyImage(FragmentActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void openImage(String image) {
        Log.d("webview", "openImage: "+image);
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra("image", image);
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void longPress(String image) {
        Log.d("webview", "longPress: "+image);
    }
}
