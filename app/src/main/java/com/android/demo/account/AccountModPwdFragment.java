package com.android.demo.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.StatusBarUtils;
import com.android.common.view.ToolbarUtil;
import com.android.common.view.ViewUtils;
import com.android.demo.R;


public class AccountModPwdFragment extends BaseFragment {

    private static final String TAG = AccountModPwdFragment.class.getSimpleName();

    public static AccountModPwdFragment newInstance() {
        return new AccountModPwdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.account_frg_modpwd);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "修改密码", View.VISIBLE);
        ToolbarUtil.setTitlebarClose(baseRootView.findViewById(R.id.iv_arrow));
        ViewUtils.setViewOnClickListener(this,baseRootView, R.id.tv_setpwd);
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
