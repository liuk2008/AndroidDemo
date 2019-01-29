package com.android.demo.account;

import android.view.View;

import com.android.common.R;
import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.ToolbarUtil;


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
