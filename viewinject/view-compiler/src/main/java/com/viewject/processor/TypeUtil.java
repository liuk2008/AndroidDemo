package com.viewject.processor;

import com.squareup.javapoet.ClassName;

/**
 * Created by mingwei on 12/15/16.
 * CSDN:    http://blog.csdn.net/u013045971
 * Github:  https://github.com/gumingwei
 */
public class TypeUtil {
    public static final ClassName FINDER = ClassName.get("com.viewinject.bindview.finder", "Finder");
    public static final ClassName VIEWINJECTOR = ClassName.get("com.viewinject.bindview", "ViewInjector");
    public static final ClassName ANDROID_ONCLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
}
