package com.android.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.fragment.BaseFragment;
import com.android.common.utils.view.ToolbarUtil;
import com.android.common.utils.view.ViewUtils;


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
        ToolbarUtil.configTitle(baseRootView, "修改密码", View.VISIBLE);
        ViewUtils.setViewOnClickListener(this, baseRootView, R.id.tv_setpwd);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.tv_setpwd) {
            replaceFragmentToStack(AccountSetPwdFragment.newInstance());
        }
    }

}
