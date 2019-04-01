package com.viewinject.bindview;

import android.support.annotation.UiThread;

import com.viewinject.bindview.finder.Finder;

import java.util.Map;

public interface ViewInjector<T> {

    @UiThread
    void inject(T t, Object source, Map<String, Integer> resIdMap, Finder finder);

    @UiThread
    void unbind();
}
