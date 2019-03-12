package com.android.demo.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.common.base.FragmentHostActivity;
import com.android.common.base.fragment.BaseFragment;
import com.android.common.base.fragment.FragmentAction;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;
import com.viewinject.annotation.MyBindView;
import com.viewinject.annotation.MyOnClick;
import com.viewinject.bindview.MyViewInjector;


public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = AccountLoginFragment.class.getSimpleName();
    private String username;
    @MyBindView(R.id.et_username)
    public EditText et_username;
    @MyBindView(R.id.tv_register)
    public TextView textView;


    public static AccountLoginFragment newInstance() {
        return new AccountLoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.account_frg_login);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "登录", View.VISIBLE);
        ToolbarUtil.setTitlebarClose(baseRootView.findViewById(R.id.iv_arrow));
        MyViewInjector.bindView(this, baseRootView);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        et_username.setText(username);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FragmentAction.FRAGMENT_RESULT_OK) {
            username = data.getStringExtra("username");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyViewInjector.unbindView(this);
    }

    @MyOnClick(R.id.tv_register)   // 在同一个Activity里面
    public void register() {
        replaceFragmentToStack(AccountRegisterFragment.newInstance(), AccountLoginFragment.this);
    }

    @MyOnClick(R.id.tv_setpwd)     // 重新打开Activity
    public void setPwd() {
        FragmentHostActivity.openFragment(getActivity(), AccountModPwdFragment.newInstance());
    }

}
