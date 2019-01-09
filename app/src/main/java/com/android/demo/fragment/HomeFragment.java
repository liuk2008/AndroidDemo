package com.android.demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.R;

public class HomeFragment extends BaseFragment {

    private String TAG = HomeFragment.class.getSimpleName();
    private View rootView;
    private TextView textView;

    public HomeFragment() {
        super();
        BASETAG = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        Log.d(TAG, "rootView : " + rootView);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initRootViews(rootView);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        setData();
        Log.d(TAG, "parent : " + parent);
        return rootView;
    }


    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        textView = baseRootView.findViewById(R.id.textview);
        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void setData() {
        Toast.makeText(getContext(), "测试", Toast.LENGTH_SHORT).show();
    }
}
