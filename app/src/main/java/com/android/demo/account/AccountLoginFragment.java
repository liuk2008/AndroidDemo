package com.android.demo.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.android.demo.R;
import com.android.demo.base.FragmentHostActivity;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.base.fragment.FragmentAction;
import com.android.demo.utils.ToolbarUtil;
import com.viewinject.annotation.MyBindView;
import com.viewinject.bindview.MyViewInjector;


public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = AccountLoginFragment.class.getSimpleName();
    private String username;
    @MyBindView(R.id.et_username)
    public EditText et_username;

    public AccountLoginFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_login);
    }

    public static AccountLoginFragment newInstance() {
        return new AccountLoginFragment();
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "登录", View.VISIBLE);
        ToolbarUtil.setTitlebarClose(baseRootView.findViewById(R.id.iv_arrow));
        setOnClickLister(R.id.tv_register, R.id.tv_setpwd);
        MyViewInjector.bind(this, baseRootView);
//        et_username = baseRootView.findViewById(R.id.et_username);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        et_username.setText(username);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_register:
                // 在同一个Activity里面
                replaceFragmentToStack(AccountRegisterFragment.newInstance(), AccountLoginFragment.this);
                break;
            case R.id.tv_setpwd:
                // 重新打开Activity
                FragmentHostActivity.openFragment(getActivity(), AccountSetPwdFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != FragmentAction.FRAGMENT_RESULT_OK) {
            return;
        } else {
            username = data.getStringExtra("username");
        }
    }
}
