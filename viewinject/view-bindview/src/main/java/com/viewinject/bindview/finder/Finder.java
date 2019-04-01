package com.viewinject.bindview.finder;

import android.support.annotation.UiThread;
import android.view.View;

import java.util.Map;

public interface Finder {
    @UiThread
    View findView(Object object, int resId);

    @UiThread
    View findView(Object object, Map<String, Integer> resIdMap, String idName);
}
