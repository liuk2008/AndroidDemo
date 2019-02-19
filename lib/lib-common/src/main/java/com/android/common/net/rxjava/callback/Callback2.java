package com.android.common.net.rxjava.callback;

public interface Callback2<X, Y> {

    void onSuccess(X x, Y y);

    void onFail(int resultCode, String msg);
}
