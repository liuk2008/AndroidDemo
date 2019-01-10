package com.android.demo.account;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.demo.R;
import com.android.demo.base.BaseFragment;
import com.android.demo.base.FragmentHostActivity;


public class AccountModPwdFragment extends BaseFragment {

    private static final String TAG = AccountModPwdFragment.class.getSimpleName();

    public AccountModPwdFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_modpwd);
    }

    public static AccountModPwdFragment newInstance() {
        return new AccountModPwdFragment();
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        TextView textView = baseRootView.findViewById(R.id.tv_setpwd);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHostActivity.openFragment(getActivity(),AccountSetPwdFragment.newInstance());
            }
        });

    }
}
