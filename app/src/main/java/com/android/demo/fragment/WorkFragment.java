package com.android.demo.fragment;

import android.view.View;
import android.widget.Toast;

import com.android.demo.R;

public class WorkFragment extends BaseFragment {

    private static final String TAG = WorkFragment.class.getSimpleName();

    public WorkFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_work);
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        Toast.makeText(getContext(), "业务", Toast.LENGTH_SHORT).show();
    }
}
