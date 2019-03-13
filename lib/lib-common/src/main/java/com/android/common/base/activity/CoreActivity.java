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
    public void addFragment(Fragment frg, Fragment sourceFragment) {
        try {
            if (sourceFragment != null)
                frg.setTargetFragment(sourceFragment, FragmentAction.FRAGMENT_REQUEST);
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commitAllowingStateLoss();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * 使用add添加fragment，需要进栈
     */
    public void addFragmentToStack(Fragment frg, Fragment sourceFragment) {
        try {
            if (sourceFragment != null)
                frg.setTargetFragment(sourceFragment, FragmentAction.FRAGMENT_REQUEST);
            String tag = frg.getClass().getName();
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, tag).addToBackStack(tag).commitAllowingStateLoss();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * 使用replace添加fragment，不需进栈
     */
    public void replaceFragment(Fragment frg, Fragment sourceFragment) {
        try {
            if (sourceFragment != null)
                frg.setTargetFragment(sourceFragment, FragmentAction.FRAGMENT_REQUEST);
            getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commitAllowingStateLoss();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * 使用replace添加fragment，需要进栈
     */
    public void replaceFragmentToStack(Fragment fragment, Fragment sourceFragment) {
        try {
            if (sourceFragment != null)
                fragment.setTargetFragment(sourceFragment, FragmentAction.FRAGMENT_REQUEST);
            String tag = fragment.getClass().getName();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(Window.ID_ANDROID_CONTENT, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();
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


}
