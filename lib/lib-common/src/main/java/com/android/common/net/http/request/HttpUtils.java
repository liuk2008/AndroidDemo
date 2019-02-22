package com.android.common.net.http.request;

import com.android.common.net.callback.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * http请求工具类
 */
public class HttpUtils {

    private static Executor executor = Executors.newCachedThreadPool();
    private static List<HttpTask> taskList = new ArrayList<>();

    public static <T> HttpTask doGet(HttpParams httpParams, Callback<T> mCallback) {
        HttpTask<T> task = new HttpTask<>(httpParams, mCallback, "GET");
        taskList.add(task);
        task.executeOnExecutor(executor);
        return task;
    }

    public static <T> HttpTask doPost(HttpParams httpParams, Callback<T> mCallback) {
        HttpTask<T> task = new HttpTask<>(httpParams, mCallback, "POST");
        taskList.add(task);
        task.executeOnExecutor(executor);
        return task;
    }

    public static void cancelTask(HttpTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
            taskList.remove(task);
            task = null;
        }
    }

    public static void cancelAll() {
        // 集合类添加元素后，将会持有元素对象的引用，导致该元素对象不能被垃圾回收，从而发生内存泄漏。
        for (int i = 0; i < taskList.size(); i++) {
            HttpTask task = taskList.get(i);
            if (task != null && !task.isCancelled()) {
                task.cancel(true);
                taskList.remove(task);
                task = null;
            }
        }
        //  清空，防止内存泄漏
        taskList.clear();
        taskList = null;
    }

}
