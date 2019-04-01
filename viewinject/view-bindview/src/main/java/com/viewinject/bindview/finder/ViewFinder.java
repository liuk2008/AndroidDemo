package com.viewinject.bindview.finder;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.view.View;

import java.util.Map;

public class ViewFinder implements Finder {

    @UiThread
    @Override
    public View findView(Object source, int resId) {
        if (source instanceof Activity) {
            return ((Activity) source).findViewById(resId);
        } else {
            return ((View) source).findViewById(resId);
        }
    }

    @UiThread
    @Override
    public View findView(Object source, Map<String, Integer> resIdMap, String idName) {
        int resId = resIdMap.get(idName);
        return findView(source, resId);
    }

}
