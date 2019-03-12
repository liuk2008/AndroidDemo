package com.android.demo.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;


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
        ToolbarUtil.configTitlebar(baseRootView, "重置密码", View.VISIBLE);
        ToolbarUtil.setTitlebarBack(baseRootView.findViewById(R.id.iv_arrow));
        setOnClickListener(baseRootView, R.id.btn_confirm);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_confirm:
                // 回退到登录页面
                getActivity().finish();
                break;
            default:
                break;
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
