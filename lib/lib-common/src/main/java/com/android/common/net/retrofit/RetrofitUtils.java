package com.android.common.net.retrofit;

import com.android.common.net.callback.Callback;

import retrofit2.Call;

public class RetrofitUtils {

    // 发送网络请求，请求执行在子线程
    public static <T> void request(Call<T> call, Callback<T> callback) {
        // 每个Call实例可以且只能执行一次请求，不能使用相同的对象再次执行execute() 或enqueue()。
        CallAdapter<T> callAdapter = new CallAdapter<>(callback);
        call.enqueue(callAdapter);
    }

    // 取消网络请求
    public static void cancel(Call call) {
        if (call != null) {
            call.cancel();
        }
    }

}
