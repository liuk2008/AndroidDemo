package com.android.demo.fragment;

import android.content.Intent;
import android.view.View;

import com.android.demo.R;
import com.android.demo.account.AccountLoginActivity;
import com.android.demo.base.fragment.BaseFragment;


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
        setOnClickLister(R.id.view_login);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.view_login:
                Intent intent = new Intent(getActivity(), AccountLoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
