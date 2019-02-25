package com.android.common.net.retrofit;

import com.android.common.net.callback.Callback;

import java.util.List;

import retrofit2.Call;

public class RetrofitUtils {

    // 发送网络请求，请求执行在子线程
    public static <T> void request(Call<T> call, Callback<T> callback) {
        // 每个Call实例可以且只能执行一次请求，不能使用相同的对象再次执行execute() 或enqueue()。
        CallAdapter<T> callAdapter = new CallAdapter<>(callback);
        call.enqueue(callAdapter);
    }

    // 取消网络请求
    public static void cancelTask(Call call) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
            call = null;
        }
    }

    public static void cancelAll(List<Call> taskList) {
        // 集合类添加元素后，将会持有元素对象的引用，导致该元素对象不能被垃圾回收，从而发生内存泄漏。
        for (int i = 0; i < taskList.size(); i++) {
            Call call = taskList.get(i);
            if (call != null && !call.isCanceled()) {
                call.cancel();
                taskList.remove(call);
                call = null;
            }
        }
        //  清空，防止内存泄漏
        taskList.clear();
        taskList = null;
    }

}
