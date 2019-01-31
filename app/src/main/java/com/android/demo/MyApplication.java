package com.android.demo;

import android.app.Application;

import com.android.utils.common.LogUtils;
import com.android.utils.exception.ThrowableConfigure;
import com.android.utils.safety.ShakeInfoUtil;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化 LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        if (!"release".equals(BuildConfig.BUILD_TYPE)) LeakCanary.install(this);

        //  初始化工具类
        LogUtils.init(this, "AndroidDemo");
        ShakeInfoUtil.init(this);
        ThrowableConfigure.init();
    }
}
