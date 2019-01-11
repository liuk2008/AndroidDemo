package com.android.demo.account;

import android.view.View;

import com.android.demo.R;
import com.android.demo.base.fragment.BaseFragment;


public class AccountSetPwdFragment extends BaseFragment {

    private static final String TAG = AccountSetPwdFragment.class.getSimpleName();

    public AccountSetPwdFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_setpwd);
    }

    public static AccountSetPwdFragment newInstance() {
        return new AccountSetPwdFragment();
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
    }
}
