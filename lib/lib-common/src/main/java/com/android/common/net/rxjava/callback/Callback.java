package com.android.common.net.rxjava.callback;


public interface Callback<T> {

    void onSuccess(T t);

    void onFail(int resultCode, String msg);

}
