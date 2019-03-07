package com.android.common.net.retrofit;


import android.content.Context;

import com.android.common.net.NetStatus;
import com.android.common.net.retrofit.converter.ConverterFactory;
import com.android.common.net.retrofit.converter.DataConverterFactory;
import com.android.common.net.retrofit.interceptor.MyOkHttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 设置Retrofit请求
 * 1、网络层200情况下
 * |--1、业务层存在数据
 * |-----1、业务层数据格式标准
 * |--------1、业务层200，处理返回数据
 * |-----------1、存在数据，使用数据model解析
 * |-----------2、不存在数据，使用Null对象解析
 * |--------2、业务层非200，抛出 ErrorException，通过ErrorHandler处理
 * |-----2、业务层数据非标准格式，使用数据model解析
 * |--2、业务层不存在数据，使用Null对象解析
 * 2、网络层非200情况下
 * |--1、网络异常时，捕获异常，通过ErrorHandler处理
 * |--2、网络正常，业务层异常通过网络层抛出时，通过ErrorHandler处理
 */
public class RetrofitEngine {

    private static final String TAG = RetrofitEngine.class.getSimpleName();
    private static final String contentType = "application/json;charset=UTF-8";
    private static RetrofitEngine retrofitEngine;
    private OkHttpClient okHttpClient;
    private Context mContext;

    public static synchronized RetrofitEngine getInstance() {
        if (retrofitEngine == null) {
            retrofitEngine = new RetrofitEngine();
        }
        return retrofitEngine;
    }

    // 初始化网络配置
    public void init(Context context) {
        mContext = context;
        okHttpClient = MyOkHttpClient.getInstance(mContext);
    }

    public <T> T getRetrofitService(Class<T> clazz, String url, int type) {
        if (null == mContext)
            throw new RuntimeException("未初始化 RetrofitEngine");
        Retrofit.Builder client = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient);
        switch (type) {
            case NetStatus.Type.RETROFIT_DEFAULT_DATAWRAPPER:
                client.addConverterFactory(new DataConverterFactory<>());
                break;
            case NetStatus.Type.RETROFIT_RXJAVA:
                client.addConverterFactory(new ConverterFactory<>());  // 必须设置在 Gson 转换之前
                client.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                client.addConverterFactory(GsonConverterFactory.create());
                break;
            case NetStatus.Type.RETROFIT_RXJAVA_DATAWRAPPER:
                client.addConverterFactory(new DataConverterFactory<>());
                client.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
                break;
            default:
                client.addConverterFactory(new ConverterFactory<>());
                client.addConverterFactory(GsonConverterFactory.create());
                break;
        }
        return client.build().create(clazz);
    }


    // 设置请求参数
    public RequestBuilder newRequestBuilder() {
        return new RequestBuilder();
    }

    public class RequestBuilder {
        private Map<String, Object> hashMap = new HashMap<>();

        public RequestBuilder append(Object key, Object value) {
            if (null == value || "".equals(String.valueOf(value))) {
                return this;
            } else {
                hashMap.put(String.valueOf(key), String.valueOf(value));
                return this;
            }
        }

        public RequestBody builder() {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String data = gson.toJson(hashMap);
            return RequestBody.create(MediaType.parse(contentType), data);
        }

    }

}
