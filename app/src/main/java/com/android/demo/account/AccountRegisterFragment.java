package com.android.demo.account;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.base.fragment.FragmentAction;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;


public class AccountRegisterFragment extends BaseFragment {

    private static final String TAG = AccountRegisterFragment.class.getSimpleName();
    public EditText et_username;

    public AccountRegisterFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_register);
    }

    public static AccountRegisterFragment newInstance() {
        return new AccountRegisterFragment();
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "注册", View.VISIBLE);
        ToolbarUtil.setTitlebarBack(baseRootView.findViewById(R.id.iv_arrow));
        et_username = baseRootView.findViewById(R.id.et_username);
        setOnClickListener(R.id.btn_register, R.id.tv_login);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_register:
                sendResult(FragmentAction.FRAGMENT_RESULT_OK);
                popBackStackImmediate();
                break;
            case R.id.tv_login:
                popBackStackImmediate();
                break;
            default:
                break;
        }
    }

    /**
     * fragment 传递返回值
     *
     * @param resultOk
     */
    private void sendResult(int resultOk) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra("username", et_username.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
        }
    }


}
