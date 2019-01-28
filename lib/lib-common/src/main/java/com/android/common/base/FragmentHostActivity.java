package com.android.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.common.base.activity.BaseActivity;

public class FragmentHostActivity extends BaseActivity {

    private static final String TAG = FragmentHostActivity.class.getSimpleName();
    private static final String FRAGMENT_NAME = "fragment_name";
    private static final String FRAGMENT_BUNDLE = "fragment_bundle";

    public static void openFragment(Context context, Fragment fragment) {
        Intent intent = newIntent(context, fragment);
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context, Fragment fragment) {
        Intent intent = new Intent(context, FragmentHostActivity.class);
        intent.putExtra(FRAGMENT_NAME, fragment.getClass().getName());
        intent.putExtra(FRAGMENT_BUNDLE, fragment.getArguments());
        if (!(context instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String fragmentClassName = intent.getStringExtra(FRAGMENT_NAME);
        Bundle arguments = intent.getBundleExtra(FRAGMENT_BUNDLE);
        Fragment fragment;
        try {
            Class<?> clazz = Class.forName(fragmentClassName);
            fragment = (Fragment) clazz.newInstance();
            fragment.setArguments(arguments);
        } catch (Exception e) {
            finish();
            e.printStackTrace();
            return;
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        replaceFragmentToStack(fragment, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + hashCode());
    }
}
