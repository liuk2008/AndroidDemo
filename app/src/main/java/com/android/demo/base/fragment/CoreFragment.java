package com.android.demo.base.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * CoreFragment：
 * 声明跟系统相关的方法
 */
public class CoreFragment extends Fragment {


    /**
     * 使用add添加fragment，需要进栈
     */
    public void addFragmentToStack(Fragment frg) {
        try {
            String tag = frg.getClass().getName();
            getFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, tag).addToBackStack(tag).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，需要进栈
     */
    public void replaceFragmentToStack(Fragment fragment) {
        try {
            String tag = fragment.getClass().getName();
            FragmentManager fragmentManager = getFragmentManager();
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
    public void addFragment(Fragment frg) {
        try {
            getFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，不需进栈
     */
    public void replaceFragment(Fragment frg) {
        try {
            getFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popFragment() {
        try {
            getFragmentManager().popBackStack();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popFragment(Class fragmentClazz, int type) {
        try {
            getFragmentManager().popBackStack(fragmentClazz.getName(), type);
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    public void printStackTrace(Throwable e) {
        e.printStackTrace();
    }


    public void showLog(String TAG, String methodName) {
        Log.d(TAG, methodName);
    }

    /**
     * hide Input 隐藏键盘
     */
    public void hideInput() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity() != null
                    && getActivity().getCurrentFocus() != null
                    && getActivity().getCurrentFocus().getWindowToken() != null
                    && null != imm) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

}
