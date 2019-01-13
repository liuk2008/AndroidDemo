package com.android.demo.account;

import android.util.Log;
import android.view.View;

import com.android.demo.R;
import com.android.demo.base.FragmentHostActivity;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.utils.ToolbarUtil;


public class AccountLoginFragment extends BaseFragment {

    private static final String TAG = AccountLoginFragment.class.getSimpleName();

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
                replaceFragmentToStack(AccountRegisterFragment.newInstance());
                break;
            case R.id.tv_setpwd:
                FragmentHostActivity.openFragment(getActivity(), AccountSetPwdFragment.newInstance());
                break;
            default:
                break;
        }
    }
}
