package com.android.demo.account;

import android.util.Log;

import com.android.demo.R;
import com.android.demo.base.BaseFragment;


public class AccountRegisterFragment extends BaseFragment {

    private static final String TAG = AccountRegisterFragment.class.getSimpleName();

    public AccountRegisterFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_register);
    }

    public static AccountRegisterFragment newInstance() {
        return new AccountRegisterFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        int number = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        Log.d(TAG, "fragmentManager : " + getActivity().getSupportFragmentManager());
    }
}
