package com.android.demo.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.android.demo.R;
import com.android.demo.base.activity.CoreActivity;
import com.android.demo.base.fragment.BaseFragment;
import com.android.demo.utils.ToolbarUtil;

public class WorkFragment extends BaseFragment {

    private static final String TAG = WorkFragment.class.getSimpleName();

    public WorkFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_work);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        View statusBar = baseRootView.findViewById(R.id.status_bar_fix);
        statusBar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ((CoreActivity) getActivity()).getStatusBarHeight()));
        statusBar.setBackgroundResource(R.drawable.shape_titlebar);
        ToolbarUtil.configTitlebar(baseRootView, "业务", View.GONE);
    }

}
