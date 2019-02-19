package com.android.common.net.rxjava.callback;


public interface Callback1<T> {

    void onSuccess(T t);

    void onFail(int resultCode, String msg, String data);

}
