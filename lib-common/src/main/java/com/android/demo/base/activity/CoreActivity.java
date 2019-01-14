package com.android.demo.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.android.demo.base.fragment.FragmentAction;

public class CoreActivity extends AppCompatActivity {

    private static final String TAG = CoreActivity.class.getSimpleName();

    /**
     * 使用add添加fragment，需要进栈
     */
    public void addFragmentToStack(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            String tag = frg.getClass().getName();
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, tag).addToBackStack(tag).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，需要进栈
     */
    public void replaceFragmentToStack(Fragment fragment, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                fragment.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            String tag = fragment.getClass().getName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(Window.ID_ANDROID_CONTENT, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * 使用add添加fragment，不需进栈
     */
    public void addFragment(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，不需进栈
     */
    public void replaceFragment(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popFragment() {
        try {
            getSupportFragmentManager().popBackStack();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popFragment(Class fragmentClazz, int type) {
        try {
            getSupportFragmentManager().popBackStack(fragmentClazz.getName(), type);
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    private void printStackTrace(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    public void getFragmentSize() {
        int number = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        for (int i = 0; i < number; i++) {
            FragmentManager.BackStackEntry backStack = getSupportFragmentManager().getBackStackEntryAt(i);
            Log.d(TAG, "name : " + backStack.getName());
        }
    }

    /**
     * 覆盖此方法，是为了让本页面的字体不随着系统设置改变，永远显示Defaults
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * hide Input 隐藏键盘
     */
    public void hideInput() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null
                    && null != imm) {
                imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * 设置状态栏颜色与标题栏颜色一致
     */
    public void configStatusBar(final int bgResource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setStatusBarBgResource(bgResource);
                    getWindow().getDecorView().removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    public void configStatusBar() {
        Window window = getWindow();
        View contentContainerView = findViewById(Window.ID_ANDROID_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            contentContainerView.setPadding(0, getStatusBarHeight(), 0, 0);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#33000000"));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            contentContainerView.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }


    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }

    /**
     * 设置状态栏背景色
     */
    public void setStatusBarBgResource(int bgResource) {
        int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
        View statusBarView = getWindow().findViewById(identifier);
        statusBarView.setBackgroundResource(bgResource);
    }


}
