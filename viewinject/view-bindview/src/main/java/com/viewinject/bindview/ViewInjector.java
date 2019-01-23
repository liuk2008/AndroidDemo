package com.viewinject.bindview;

import android.view.View;

public interface ViewInjector<T> {
    void inject(T t);
    void inject(T t, View view);
}
