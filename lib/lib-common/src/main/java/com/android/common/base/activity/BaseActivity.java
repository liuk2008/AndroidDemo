package com.android.common.base.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.common.R;
import com.android.common.utils.StatusBarUtils;


public class BaseActivity extends CoreActivity implements View.OnClickListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.configStatusBarBgRes(this, R.drawable.common_titlebar_shape); // 设置状态栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置屏幕方向
    }

    @Override
    public void onClick(View view) {
        if (null == view)
            return;
    }

}
