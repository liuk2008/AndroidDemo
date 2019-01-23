package com.viewinject.bindview;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MyViewInjector {

    private static final Map<String, ViewInjector> injectorMap = new HashMap<>();

    public static void bind(Activity activity) {
        bind(activity);
    }

    public static void bind(Fragment fragment, View view) {
        bind(fragment, view);
    }

    private static void bind(Object object, View view) {
        String className = object.getClass().getName();
        try {
            ViewInjector injector = injectorMap.get(className);
            if (injector == null) {
                String proxyClassName = className + "ViewInjector";
                Class<?> clazz = Class.forName(proxyClassName);
                injector = (ViewInjector) clazz.newInstance();
                injectorMap.put(className, injector);
            }
            if (view != null) {
                injector.inject(object, view);
            } else {
                injector.inject(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
