package com.android.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.R;

public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View rootView;
    private TextView textView;

    public HomeFragment() {
        super();
        BASETAG = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initRootViews(rootView);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        Log.d(TAG, "rootView : " + rootView);
        Log.d(TAG, "parent : " + parent);
        return rootView;
    }


    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        textView = baseRootView.findViewById(R.id.textview);
    }

    @Override
    protected void afterCreateView(@Nullable Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);
        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
