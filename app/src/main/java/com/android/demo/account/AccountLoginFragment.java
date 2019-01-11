package com.android.demo.account;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.demo.R;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.base.FragmentHostActivity;


public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = AccountLoginFragment.class.getSimpleName();

    public AccountLoginFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_login);
    }

    public static AccountLoginFragment newInstance() {
        return new AccountLoginFragment();
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        TextView tv_register = baseRootView.findViewById(R.id.tv_register);
        TextView tv_setpwd = baseRootView.findViewById(R.id.tv_setpwd);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragmentToStack(AccountRegisterFragment.newInstance());
            }
        });
        tv_setpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHostActivity.openFragment(getActivity(), AccountSetPwdFragment.newInstance());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        int number = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        Log.d(TAG, "fragmentManager : " + getActivity().getSupportFragmentManager());
    }
}
