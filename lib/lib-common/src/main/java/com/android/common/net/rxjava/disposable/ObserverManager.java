package com.android.common.net.rxjava.disposable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ObserverManager implements ObserverHelper {

    private static ObserverManager manager = null;
    private CompositeDisposable mDisposable;

    private ObserverManager() {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
    }

    public static synchronized ObserverManager getInstance() {
        if (manager == null) {
            manager = new ObserverManager();
        }
        return manager;
    }

    //  添加到集合中
    @Override
    public void add(Disposable disposable) {
        if (disposable != null && mDisposable != null) {
            mDisposable.add(disposable);
        }
    }

    // 从集合中移除
    @Override
    public void cancel(Disposable disposable) {
        if (disposable != null && mDisposable != null) {
            mDisposable.remove(disposable);
        }
    }

    @Override
    public void cancelAll() {
        if (mDisposable == null) return;
        mDisposable.clear();
    }
}
