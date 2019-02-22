package com.android.common.net.callback;

public interface Callback1<X, Y> {

    void onSuccess(X x, Y y);

    void onFail(int resultCode, String msg, String data);

}
