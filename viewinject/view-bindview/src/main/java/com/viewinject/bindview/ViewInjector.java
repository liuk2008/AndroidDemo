package com.viewinject.bindview;

import com.viewinject.bindview.finder.Finder;

public interface ViewInjector<T> {
    void inject(T t, Object source, Finder finder);
}
