package com.android.demo;

import android.app.Application;

import com.android.utils.common.LogUtils;
import com.android.utils.exception.ThrowableConfigure;
import com.android.utils.safety.ShakeInfoUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init(this, "AndroidDemo");
        ShakeInfoUtil.init(this);
        ThrowableConfigure.init();
    }
}
