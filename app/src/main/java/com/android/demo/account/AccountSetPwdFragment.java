package com.android.demo.account;

import android.view.View;

import com.android.demo.R;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.utils.ToolbarUtil;


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
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "重置密码", View.VISIBLE);
        ToolbarUtil.setTitlebarClose(baseRootView.findViewById(R.id.iv_arrow));
    }
}
