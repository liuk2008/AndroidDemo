package com.android.account;

import android.os.Bundle;

import com.android.base.activity.BaseActivity;


public class AccountLoginActivity extends BaseActivity {

    private static final String TAG = AccountLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragmentToStack(AccountLoginFragment.newInstance());
    }

}
