package com.android.common.net.retrofit.interceptor;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置Retrofit请求头
 */
public class MyHttpHeaderInterceptor implements Interceptor {

    private static final String TAG = MyHttpHeaderInterceptor.class.getSimpleName();
    private static String deviceId = "known";
    private static String osVersion;
    private static String userAgent;
    private static String versionName;
    private static int versionCode;
    private static MyHttpHeaderInterceptor instance;

    private MyHttpHeaderInterceptor(Context context) {
        init(context);
    }

    public static MyHttpHeaderInterceptor getInstance(Context context) {
        // 双重校验锁 在JDK1.5之后，双重检查锁定才能够正常达到单例效果。
        if (null == instance) {
            synchronized (MyHttpHeaderInterceptor.class) {
                if (null == instance) {
                    instance = new MyHttpHeaderInterceptor(context);
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
            userAgent = "OS/android;"
                    + "OSVersion/" + Build.VERSION.SDK_INT + ";"
                    + "phoneBrand/" + Build.MANUFACTURER + ";"
                    + "screenHeight/" + screenHeight + ";"
                    + "screenWidth/" + screenWidth;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void configHeader(Request.Builder builder) {
        try {
            addHeader(builder, "user-agent", userAgent);
            addHeader(builder, "versionName", versionName);//重要
            addHeader(builder, "versionCode", versionCode);//重要
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHeader(Request.Builder builder, String key, Object value) {
        try {
            if (null != value) {
                builder.removeHeader(key);
                builder.addHeader(key, String.valueOf(value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        configHeader(builder);
        Request requestWithUserAgent = builder.build();
        return chain.proceed(requestWithUserAgent);
    }
}
