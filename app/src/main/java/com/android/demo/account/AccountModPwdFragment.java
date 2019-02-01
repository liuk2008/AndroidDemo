package com.android.demo.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;


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
        ToolbarUtil.configTitlebar(baseRootView, "修改密码", View.VISIBLE);
        ToolbarUtil.setTitlebarClose(baseRootView.findViewById(R.id.iv_arrow));
        setOnClickListener(R.id.tv_setpwd);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_setpwd:
                replaceFragmentToStack(AccountSetPwdFragment.newInstance());
                break;
            default:
                break;
        }
    }

}
