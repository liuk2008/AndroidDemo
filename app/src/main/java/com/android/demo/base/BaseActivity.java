package com.android.demo.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * 使用add添加fragment，需要进栈
     */
    public void addFragmentToStack(Fragment frg) {
        try {
            String tag = frg.getClass().getName();
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, tag).addToBackStack(tag).commit();
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
    public void addFragment(Fragment frg) {
        try {
            getSupportFragmentManager().beginTransaction().add(Window.ID_ANDROID_CONTENT, frg, frg.getClass().getName()).commit();
        } catch (Throwable e) {
            printStackTrace(e);
        }
    }


    /**
     * 使用replace添加fragment，不需进栈
     */
    public void replaceFragment(Fragment frg) {
        try {
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

    private void getFragmentSize() {
        int number = getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        for (int i = 0; i < number; i++) {
            FragmentManager.BackStackEntry backStack = getSupportFragmentManager().getBackStackEntryAt(i);
            Log.d(TAG, "name : " + backStack.getName());
        }
    }


}
