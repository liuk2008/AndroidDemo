package com.android.demo.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

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

    private void printStackTrace(Throwable e) {
        e.printStackTrace();
    }

}
