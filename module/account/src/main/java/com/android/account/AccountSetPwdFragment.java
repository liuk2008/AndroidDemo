package com.android.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.fragment.BaseFragment;
import com.android.common.utils.view.ToolbarUtil;
import com.android.common.utils.view.ViewUtils;


public class AccountSetPwdFragment extends BaseFragment {

    private static final String TAG = AccountSetPwdFragment.class.getSimpleName();


    public static AccountSetPwdFragment newInstance() {
        return new AccountSetPwdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.account_frg_setpwd);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitleBack(baseRootView, "重置密码");
        ViewUtils.setViewOnClickListener(this, baseRootView, R.id.btn_confirm);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.btn_confirm) {
            // 回退到登录页面
            getActivity().finish();
        }
    }


    /**
     * 捕捉返回键
     */
    @Override
    public boolean onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("确定不修改")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popBackStackImmediate();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        return true;
    }

}
