package com.android.demo.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.R;


public class MineFragment extends BaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    public MineFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_mine);
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        TextView textView = baseRootView.findViewById(R.id.textview);
        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
