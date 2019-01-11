package com.android.demo.fragment;

import android.view.View;

import com.android.demo.R;
import com.android.demo.account.AccountModPwdFragment;
import com.android.demo.base.FragmentHostActivity;
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
        setOnClickLister(R.id.btn);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn:
                FragmentHostActivity.openFragment(getActivity(), AccountModPwdFragment.newInstance());
                break;
            default:
                break;
        }
    }

}
