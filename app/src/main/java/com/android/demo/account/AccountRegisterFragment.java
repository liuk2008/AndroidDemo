package com.android.demo.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    public static AccountRegisterFragment newInstance() {
        return new AccountRegisterFragment();
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);

        ToolbarUtil.configTitlebar(baseRootView, "注册", View.VISIBLE);
        ToolbarUtil.setTitlebarBack(baseRootView.findViewById(R.id.iv_arrow));

        et_username = baseRootView.findViewById(R.id.et_username);
        setOnClickLister(R.id.btn_register, R.id.tv_term);

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

    /**
     * fragment 传递返回值
     *
     * @param resultOk
     */
    private void sendResult(int resultOk) {
        if (getTargetFragment() == null) {
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("username", et_username.getText().toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
        }
    }

    @Override
    public boolean onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("测试")
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
