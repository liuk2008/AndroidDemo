package com.android.common.net.rxjava.disposable;

import io.reactivex.disposables.Disposable;

public interface ObserverHelper {

    void add(Disposable disposable);

    void cancel(Disposable disposable);

    void cancelAll();

}
