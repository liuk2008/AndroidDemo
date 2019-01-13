package com.android.demo.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.demo.base.R;


public class BaseActivity extends CoreActivity implements View.OnClickListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configStatusBar(R.drawable.shape_titlebar);
    }

    /**
     * View相关方法
     */
    public void setVisible(View... views) {
        for (View view : views) {
            setViewStatus(view, View.VISIBLE);
        }
    }

    public void setVisible(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.VISIBLE);
        }
    }

    public void setInvisible(View... views) {
        for (View view : views) {
            setViewStatus(view, View.INVISIBLE);
        }
    }

    public void setInvisible(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.INVISIBLE);
        }
    }

    public void setGone(View... views) {
        for (View view : views) {
            setViewStatus(view, View.GONE);
        }
    }

    public void setGone(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            setViewStatus(view, View.GONE);
        }
    }

    private void setViewStatus(View view, int type) {
        if (null != view && view.getVisibility() != type)
            view.setVisibility(type);
    }


    public void setText(@IdRes int textViewId, @Nullable Object text) {
        TextView textView = findViewById(textViewId);
        setText(textView, text);
    }

    public void setText(TextView textView, @Nullable Object text) {
        if (null != textView && null != text) {
            if (text instanceof CharSequence)
                textView.setText((CharSequence) text);
            else
                textView.setText(String.valueOf(text));
        }
    }


    @Override
    public void onClick(View view) {
        if (null == view)
            return;
    }

    public void setOnClickLister(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void setOnClickLister(int... ids) {
        for (int id : ids) {
            View view = findViewById(id);
            if (null != view)
                view.setOnClickListener(this);
        }
    }

}
