package com.android.demo.account;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.demo.base.BaseActivity;

public class AccountLoginActivity extends BaseActivity {

    private static final String TAG = AccountLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragmentToStack(AccountLoginFragment.newInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFragmentSize();
        Log.d(TAG, "hashcode : " + this.hashCode());
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
