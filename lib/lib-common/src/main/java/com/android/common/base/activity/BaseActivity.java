package com.android.common.base.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.common.base.R;
import com.android.utils.utils.common.LogUtils;
import com.android.utils.utils.system.NetUtils;


public class BaseActivity extends CoreActivity implements View.OnClickListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private BroadcastReceiver netReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configStatusBar(R.drawable.shape_titlebar); // 设置状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置屏幕方向
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 当Activity中存在Fragment时，使用Fragment的网络检测方法，没有Fragment时，调用Activity方法检测网络
        if (getSupportFragmentManager().getFragments().size() <= 0) {
            checkNet();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != netReceiver) {
            unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }

    /**
     * View相关方法
     */
    public void setVisible(View... views) {
        for (View view : views) {
            setViewStatus(view, View.VISIBLE);
        }
    }

    public void setVisible(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.VISIBLE);
        }
    }

    public void setInvisible(View... views) {
        for (View view : views) {
            setViewStatus(view, View.INVISIBLE);
        }
    }

    public void setInvisible(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.INVISIBLE);
        }
    }

    public void setGone(View... views) {
        for (View view : views) {
            setViewStatus(view, View.GONE);
        }
    }

    public void setGone(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.GONE);
        }
    }

    private void setViewStatus(View view, int type) {
        if (null != view && view.getVisibility() != type)
            view.setVisibility(type);
    }


    public void setText(@IdRes int textViewId, @Nullable Object text) {
        TextView textView = findViewById(textViewId);
        setText(textView, text);
    }

    public void setText(TextView textView, @Nullable Object text) {
        if (null != textView && null != text) {
            if (text instanceof CharSequence)
                textView.setText((CharSequence) text);
            else
                textView.setText(String.valueOf(text));
        }
    }

    @Override
    public void onClick(View view) {
        if (null == view)
            return;
    }

    public void setOnClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void setOnClickListener(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (null != view)
                view.setOnClickListener(this);
        }
    }

    public void checkNet() {
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isConnected = NetUtils.isNetConnected(context);
                View net_tips = findViewById(R.id.tv_net_tips);
                if (null != net_tips) {
                    net_tips.setVisibility(isConnected ? View.GONE : View.VISIBLE);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netReceiver, filter);
    }

}
