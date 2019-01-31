package com.android.demo.fragment;

import android.content.Intent;
import android.view.View;

import com.android.common.base.fragment.BaseFragment;
import com.android.demo.R;
import com.android.demo.account.AccountLoginActivity;
import com.viewinject.annotation.MyOnClick;
import com.viewinject.bindview.MyViewInjector;

/**
 * 状态栏透明
 */
public class MineFragment extends BaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    public MineFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_mine);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        MyViewInjector.bindView(this, baseRootView);
    }

    @MyOnClick(R.id.view_login)
    public void login() {
        Intent intent = new Intent(getActivity(), AccountLoginActivity.class);
        startActivity(intent);
    }

}
