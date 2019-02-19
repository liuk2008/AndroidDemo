package com.android.common.net.retrofit.interceptor;


import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 初始化OkHttpClient
 */
public class MyOkHttpClient {

    private static OkHttpClient instance;
    private static final int TIME = 15;

    public static OkHttpClient getInstance(Context context) {
        if (null == instance) {
            synchronized (MyOkHttpClient.class) {
                if (null == instance) {
                    try {
                        init(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    // 提供外部方法，设置自定义OkHttpClient
    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        if (null != instance)
            return;
        instance = okHttpClient;
    }

    private static void init(Context context) {
        // 设置日志
        MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor();
        loggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY);
        // 设置请求头
        MyHttpHeaderInterceptor headerInterceptor = MyHttpHeaderInterceptor.getInstance(context);
        // 设置缓存
        File cacheDir = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);//缓存10m
        MyHttpCacheInterceptor cacheInterceptor = new MyHttpCacheInterceptor(context);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME, TimeUnit.SECONDS)
                .writeTimeout(TIME, TimeUnit.SECONDS)
                .readTimeout(TIME, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
//                .cookieJar(new MyHttpCookieInterceptor()) // 设置cookie
//                .retryOnConnectionFailure(false) // 禁止重新连接
//                .connectionPool(new ConnectionPool(0, 1, TimeUnit.SECONDS))
//                .cache(cache) // 设置缓存目录    // 设置缓存
////                .addInterceptor(cacheInterceptor)
                .addInterceptor(loggingInterceptor);
        instance = builder.build();

        // 应用拦截器  让所有网络请求都附上你的拦截器。设置重试
        // 当网络链路异常时，也就是网络请求未发送出去时，网络请求无响应，NetworkInterceptor会抛出异常，不会尝试重新连接
//                                .addInterceptor(new MyHttpNetworkInterceptor(2))
        // 当网络链路正常时，自定义请求重试时，发送的请求必须成功一次后才能尝试再次连接，否则会崩溃
        // 当网络链路异常时，也就是网络请求未发送出去时，网络请求无响应，会抛出网络异常，不会崩溃，且不会尝试重新连接
//                                .addInterceptor(new MyHttpNetworkInterceptor(2)); // 慎用

    }

}
