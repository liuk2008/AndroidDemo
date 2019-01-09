package com.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.R;


public class MineFragment extends BaseFragment {

    private String TAG = MineFragment.class.getSimpleName();
    private TextView textView;

    public MineFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_mine);
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        textView = baseRootView.findViewById(R.id.textview);
        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
