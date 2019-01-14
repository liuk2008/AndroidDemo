package com.android.demo.account;

import android.view.View;
import android.widget.TextView;

import com.android.demo.R;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.base.FragmentHostActivity;
import com.android.demo.utils.ToolbarUtil;


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
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView,"修改密码",View.GONE);
        TextView textView = baseRootView.findViewById(R.id.tv_setpwd);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHostActivity.openFragment(getActivity(),AccountSetPwdFragment.newInstance());
            }
        });

    }
}