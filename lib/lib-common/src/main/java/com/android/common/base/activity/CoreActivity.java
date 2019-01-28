package com.android.common.base.activity;

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

import com.android.common.base.fragment.CoreFragment;
import com.android.common.base.fragment.FragmentAction;

import java.util.List;

public class CoreActivity extends AppCompatActivity {

    private static final String TAG = CoreActivity.class.getSimpleName();

    public void addFragment(Fragment frg) {
        addFragment(frg, null);
    }

    public void addFragmentToStack(Fragment frg) {
        addFragmentToStack(frg, null);
    }

    public void replaceFragment(Fragment frg) {
        replaceFragment(frg, null);
    }

    public void replaceFragmentToStack(Fragment frg) {
        replaceFragmentToStack(frg, null);
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
     * fragment回退栈
     * popBackStack()是弹出默认的最上层的栈顶内容
     * 1、回滚是以提交的事务为单位进行的
     * 2、调用该方法后会将事物操作插入到FragmentManager的操作队列，只有当轮询到该事物时才能执行。
     */
    public void popFragment() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                getSupportFragmentManager().popBackStack();
            else
                finish();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     * int flags有两个取值：0或FragmentManager.POP_BACK_STACK_INCLUSIVE；
     * 当取值0时，表示除了参数一指定这一层之上的所有层都退出栈，指定的这一层为栈顶层； 
     * 当取值POP_BACK_STACK_INCLUSIVE时，表示连着参数一指定的这一层一起退出栈
     */
    public void popFragment(Class fragmentClazz, int type) {
        try {
            getSupportFragmentManager().popBackStack(fragmentClazz.getName(), type);
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * fragment回退栈
     * 调用该方法后会将事物操作插入到FragmentManager的操作队列，立即执行事物
     */
    public void popBackStackImmediate() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                getSupportFragmentManager().popBackStackImmediate();
            else
                finish();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popBackStackImmediate(Class fragmentClazz, int type) {
        try {
            getSupportFragmentManager().popBackStackImmediate(fragmentClazz.getName(), type);
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    private void printStackTrace(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onBackPressed() {
        // 处理Fragment中的返回键
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment.isResumed() && fragment.getUserVisibleHint() && fragment instanceof CoreFragment) {
                    if (fragment instanceof CoreFragment) {
                        CoreFragment f = (CoreFragment) fragment;
                        if (f.onBackPressed()) {
                            return;
                        }
                    }
                }
            }
        }
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

    /**
     * 设置状态栏透明
     */
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
