package com.android.common.utils;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.R;


public class ToolbarUtil {

    private static final String TAG = ToolbarUtil.class.getSimpleName();

    public static void configActivityTitle(final Activity activity, String title) {
        View rootView = activity.findViewById(android.R.id.content);
        configTitlebar(rootView, title, View.GONE, 0);
    }

    public static void configActivityTitleClose(final Activity activity, String title) {
        View rootView = activity.findViewById(android.R.id.content);
        configTitlebar(rootView, title, View.VISIBLE, 1);
    }

    public static void configFragmentTitle(View rootView, String title) {
        configTitlebar(rootView, title, View.GONE, 0);
    }

    public static void configFragmentTitleBack(View rootView, String title) {
        configTitlebar(rootView, title, View.VISIBLE, 2);

    }

    public static void configFragmentTitleClose(View rootView, String title) {
        configTitlebar(rootView, title, View.VISIBLE, 3);
    }

    private static void configTitlebar(View rootView, String title, int visible, final int type) {
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        ImageView ivArrow = rootView.findViewById(R.id.iv_arrow);
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);
        ivArrow.setVisibility(visible);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getRootView().getContext();
                if (type == 1 || type == 3) {
                    if (context instanceof Activity) ((Activity) context).finish();
                    else {
                        context = v.getContext();
                        if (context instanceof Activity) ((Activity) context).finish();
                    }
                } else if (type == 2) {
                    if (context instanceof Activity) ((Activity) context).onBackPressed();
                    else {
                        context = v.getContext();
                        if (context instanceof Activity) ((Activity) context).onBackPressed();
                    }
                }
            }
        });

    }

}
