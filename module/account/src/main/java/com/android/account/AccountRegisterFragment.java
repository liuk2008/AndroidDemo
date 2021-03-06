package com.android.account;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.base.fragment.BaseFragment;
import com.android.base.fragment.FragmentAction;
import com.android.common.utils.view.ToolbarUtil;
import com.android.common.utils.view.ViewUtils;


public class AccountRegisterFragment extends BaseFragment {

    private static final String TAG = AccountRegisterFragment.class.getSimpleName();
    public EditText et_username;


    public static AccountRegisterFragment newInstance() {
        return new AccountRegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.account_frg_register);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitleBack(baseRootView, "注册");
        et_username = baseRootView.findViewById(R.id.et_username);
        ViewUtils.setViewOnClickListener(this, baseRootView, R.id.btn_register, R.id.tv_login);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.btn_register) {
            sendResult(FragmentAction.FRAGMENT_RESULT_OK);
            popBackStackImmediate();
        } else if (id == R.id.tv_login) {
            popBackStackImmediate();
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
