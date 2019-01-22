package com.android.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.demo.base.R;


public class ToolbarUtil {
    private static final String TAG = ToolbarUtil.class.getSimpleName();

    public static void configTitlebar(View rootView, String title, int type) {
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        ImageView ivArrow = rootView.findViewById(R.id.iv_arrow);
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);
        if (View.VISIBLE == type
                || View.INVISIBLE == type
                || View.GONE == type)
            ivArrow.setVisibility(type);
    }

    /**
     * 返回键正常返回
     *
     * @param view
     */
    public static void setTitlebarBack(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getRootView().getContext();
                if (context instanceof Activity) ((Activity) context).onBackPressed();
                else {
                    context = v.getContext();
                    if (context instanceof Activity) ((Activity) context).onBackPressed();
                }
            }
        });
    }

    /**
     * 返回键关闭当前Activity
     *
     * @param view
     */
    public static void setTitlebarClose(final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getRootView().getContext();
                if (context instanceof Activity) ((Activity) context).finish();
                else {
                    context = v.getContext();
                    if (context instanceof Activity) ((Activity) context).finish();
                }
            }
        });
    }
}
