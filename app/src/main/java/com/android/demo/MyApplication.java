package com.android.demo;

import android.app.Application;


import com.android.common.utils.common.LogUtils;
import com.android.common.utils.exception.ThrowableConfigure;
import com.android.common.utils.safety.ShakeInfoUtil;
import com.android.network.http.engine.HttpEngine;
import com.android.network.retrofit.RetrofitEngine;
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

        // 初始化网络框架
        HttpEngine.getInstance().init(this);
        RetrofitEngine retrofitEngine = RetrofitEngine.getInstance();
//        retrofitEngine.configHttpClient(null); // 可自定义OkHttpClient
        retrofitEngine.isProxy(false);
        retrofitEngine.init(this);


    }
}
