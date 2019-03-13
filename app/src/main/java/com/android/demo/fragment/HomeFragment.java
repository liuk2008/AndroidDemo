package com.android.demo.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.StatusBarUtils;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;

/**
 * 状态栏透明
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View viewTitle;
    private int scrollMaxHeight;

    {
        super.TAG = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.fragment_home);
    }

    @Override
    @RequiresApi(Build.VERSION_CODES.M)
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "首页", View.GONE);
        ViewGroup view = baseRootView.findViewById(R.id.view_test);
        for (int i = 0; i < 60; i++) {
            TextView textView = new TextView(getContext());
            textView.setText("测试" + i);
            textView.setTextColor(Color.BLACK);
            view.addView(textView, i);
        }
        View scrollView = baseRootView.findViewById(R.id.scrollView);
        viewTitle = baseRootView.findViewById(R.id.view_title);
        viewTitle.setAlpha(0);
        // 设置系统状态栏透明，通过设置标题栏Padding高度=状态栏高度，保持颜色一致
        viewTitle.setPadding(0, StatusBarUtils.getStatusBarHeight(getActivity()), 0, 0);
        scrollMaxHeight = (getResources().getDisplayMetrics().heightPixels) / 3;
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float alpha = 0;
                if (scrollY <= 0) {
                    viewTitle.setAlpha(0);
                } else if (scrollY < scrollMaxHeight) {
                    alpha = 1 - (scrollMaxHeight - scrollY) * 1f / scrollMaxHeight;
                    viewTitle.setAlpha(alpha);
                } else {
                    viewTitle.setAlpha(1);
                }
            }
        });
    }
}
