package com.android.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.account.AccountLoginActivity;
import com.android.base.fragment.BaseFragment;
import com.android.demo.R;
import com.viewinject.annotation.MyOnClick;
import com.viewinject.bindview.MyViewInjector;

/**
 * 状态栏透明
 */
public class MineFragment extends BaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    {
        super.TAG = TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return setRootView(inflater, R.layout.fragment_mine);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyViewInjector.unbindView(this);
    }


}
