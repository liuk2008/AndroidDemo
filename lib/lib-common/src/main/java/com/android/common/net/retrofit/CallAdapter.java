package com.android.common.net.retrofit;

import android.util.Log;

import com.android.common.net.callback.Callback;
import com.android.common.net.error.ErrorData;
import com.android.common.net.error.ErrorHandler;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by liuk on 2018/3/29 0029.
 */
public class CallAdapter<T> implements retrofit2.Callback<T> {

    private static final String TAG = CallAdapter.class.getSimpleName();
    private Callback<T> mCallback;

    public CallAdapter(Callback<T> callback) {
        mCallback = callback;
    }

    // retrofit 响应结果执行在UI线程，不过OkHttp的onResponse结果执行在子线程中
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) { // 网络层200
            try {
                T t = response.body(); // 注意 response 不能被解析的情况下，response.body()会返回null
                mCallback.onSuccess(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {     // 404，500 时执行
            ResponseBody errorBody = response.errorBody();
            String result = null;
            try {
                result = new String(errorBody.bytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCallback.onFail(response.code(), "网络异常", result);
        }
    }

    //  当一个请求取消时，回调方法onFailure()会执行，而onFailure()方法在没有网络或网络超时的时候也会执行。
    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        Log.d(TAG, "onFailure: ");
        throwable.printStackTrace();
        if (!call.isCanceled()) {
            ErrorData errorData = ErrorHandler.handlerError(throwable);
            mCallback.onFail(errorData.getCode(), errorData.getMsg(), errorData.getData());
        } else {
            Log.d(TAG, "onFailure: 取消网络请求");
        }
    }

    public void setCallback() {
        mCallback = null;
    }
}
