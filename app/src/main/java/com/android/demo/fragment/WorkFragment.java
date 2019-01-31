package com.android.demo.fragment;

import android.Manifest;
import android.view.View;
import android.widget.LinearLayout;

import com.android.common.base.PermissionActivity;
import com.android.common.base.activity.CoreActivity;
import com.android.common.base.fragment.BaseFragment;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;
import com.viewinject.bindview.MyViewInjector;

/**
 * 通过代码动态设置状态栏与主题色一致
 * 1、设置布局View，高度为0dp
 * 2、获取状态栏高度，设置布局View高度=状态栏高度
 * 3、设置布局View背景色与主题色一致
 */
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
        MyViewInjector.bindView(this, baseRootView);

        PermissionActivity.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                null);

    }

}
