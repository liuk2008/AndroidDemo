package com.android.demo.base.fragment;

import android.app.Activity;
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

    private static final String TAG = CoreFragment.class.getSimpleName();
    private boolean mHandledPress = false;

    /**
     * 使用add添加fragment，需要进栈
     */
    public void addFragmentToStack(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            String tag = frg.getClass().getName();
            getFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, tag).addToBackStack(tag).commit();
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
    public void addFragment(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            getFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，不需进栈
     * Window.ID_ANDROID_CONTENT：
     */
    public void replaceFragment(Fragment frg, Fragment tagFragment) {
        try {
            if (tagFragment != null)
                frg.setTargetFragment(tagFragment, FragmentAction.FRAGMENT_REQUEST);
            getFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
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
            getFragmentManager().popBackStack();
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
            getFragmentManager().popBackStack(fragmentClazz.getName(), type);
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
            getFragmentManager().popBackStackImmediate();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }

    /**
     * fragment回退栈
     */
    public void popBackStackImmediate(Class fragmentClazz, int type) {
        try {
            getFragmentManager().popBackStackImmediate(fragmentClazz.getName(), type);
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

    /**
     * 如果对返回事件进行了处理就返回TRUE,如果不做处理就返回FALSE,让上层进行处理。
     *
     * @return
     */
    public boolean onBackPressed() {
        if (!mHandledPress) {
            Log.d(TAG, "onBackPressed:捕捉到了回退事件哦！");
            mHandledPress = true;
            return true;
        }
        return false;
    }
}
