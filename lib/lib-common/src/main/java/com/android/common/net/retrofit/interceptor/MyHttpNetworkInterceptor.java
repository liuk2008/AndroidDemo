package com.android.common.net.retrofit.interceptor;



import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * TODO 待验证
 * OkHttp 中的拦截器分成应用和网络拦截器两种。应用拦截器对于每个 HTTP 响应都只会调用一次，可以通过不调用 Chain.proceed 方法来终止请求，
 * 也可以通过多次调用 Chain.proceed 方法来进行重试。网络拦截器对于调用执行中的自动重定向和重试所产生的响应也会被调用，而如果响应来自缓存，则不会被调用。
 * 需考虑重置尝试次数
 * <p>
 * MyHttpNetworkInterceptor@22fa38d must call proceed() exactly once
 * <p>
 * Created by Administrator on 2018/2/6.
 */

public class MyHttpNetworkInterceptor implements Interceptor {
    private static final String TAG = MyHttpNetworkInterceptor.class.getSimpleName();
    private int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public MyHttpNetworkInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 当网络链路正常时，请求有响应时，也就是服务器异常的话，会尝试重新连接
        // 当网络链路异常时，也就是网络请求未发送出去时，网络请求无响应，会抛出异常，不会尝试重新连接
//        LogUtils.logd(TAG, "retryNum=" + retryNum);
//        LogUtils.logd(TAG, "request=" + request);
//        LogUtils.logd(TAG, "chain=" + chain);
//        LogUtils.logd(TAG, "response=" + response);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            // 请求重试必须成功一次后才能尝试再次连接
            response = chain.proceed(request);
//            LogUtils.logd(TAG, "===============");
//            LogUtils.logd(TAG, "retryNum=" + retryNum);
//            LogUtils.logd(TAG, "chain=" + chain);
//            LogUtils.logd(TAG, "response:" + response);
        }
        retryNum = 0;
        return response;
    }

}
