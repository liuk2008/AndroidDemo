package com.android.demo.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.demo.R;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.base.fragment.FragmentAction;
import com.android.demo.utils.ToolbarUtil;


public class AccountRegisterFragment extends BaseFragment {

    private static final String TAG = AccountRegisterFragment.class.getSimpleName();
    private EditText et_username;

    public AccountRegisterFragment() {
        BASETAG = TAG;
        setLayoutId(R.layout.account_frg_register);
    }

    public static AccountRegisterFragment newInstance(String username) {
        AccountRegisterFragment fragment = new AccountRegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        int number = getActivity().getSupportFragmentManager().getBackStackEntryCount();
        Log.d(TAG, "number : " + number);
        Log.d(TAG, "fragmentManager : " + getActivity().getSupportFragmentManager());
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);

        Bundle arguments = getArguments();
        String username = arguments.getString("username");

        ToolbarUtil.configTitlebar(baseRootView, "注册", View.VISIBLE);
        ToolbarUtil.setTitlebarBack(baseRootView.findViewById(R.id.iv_arrow));

        setOnClickLister(R.id.btn_register, R.id.tv_term);

        et_username = baseRootView.findViewById(R.id.et_username);
        et_username.setText(username);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_register:
                sendResult(FragmentAction.FRAGMENT_RESULT_OK);
                popBackStackImmediate();
                break;
            case R.id.tv_term:
                popBackStackImmediate();
                break;
            default:
                break;
        }
    }

    private void sendResult(int resultOk) {
        if (getTargetFragment() == null) {
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("username", et_username.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
        }
    }

}
