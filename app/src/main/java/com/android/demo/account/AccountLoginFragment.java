package com.android.demo.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.demo.R;
import com.android.demo.base.FragmentHostActivity;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.base.fragment.FragmentAction;
import com.android.demo.utils.ToolbarUtil;


public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = AccountLoginFragment.class.getSimpleName();
    private EditText et_username;
    private String username;

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
        et_username = baseRootView.findViewById(R.id.et_username);
    }

    @Override
    public void afterCreateView(@Nullable Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);
    }

    /**
     * 当fragment已存在时，重新加载会执行onViewStateRestored把原有的控件数据重新赋值回来。
     * onViewStateRestored在onActivityCreated(Bundle)后面执行，所以onViewCreated里面的mobileEt被覆盖掉了。
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        et_username.setText(username);
    }

    @Override
    public void onResume() {
        super.onResume();
        int number = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        Log.d(TAG, "fragmentManager : " + getActivity().getSupportFragmentManager());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_register:
                replaceFragmentToStack(AccountRegisterFragment.newInstance(et_username.getText().toString()), AccountLoginFragment.this);
                break;
            case R.id.tv_setpwd:
                FragmentHostActivity.openFragment(getActivity(), AccountSetPwdFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + resultCode);
        if (resultCode != FragmentAction.FRAGMENT_RESULT_OK) {
            return;
        } else {
            username = data.getStringExtra("username");
        }
        setText(R.id.textview,username);
    }
}
