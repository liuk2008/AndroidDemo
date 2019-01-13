package com.android.demo.account;

import android.os.Bundle;
import android.util.Log;

import com.android.demo.base.activity.BaseActivity;

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
    }
}
