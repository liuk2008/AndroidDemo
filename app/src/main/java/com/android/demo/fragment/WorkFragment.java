package com.android.demo.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.demo.R;
import com.android.demo.account.AccountLoginActivity;
import com.android.demo.base.BaseFragment;

public class WorkFragment extends BaseFragment {

    private static final String TAG = WorkFragment.class.getSimpleName();

    public WorkFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_work);
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        Toast.makeText(getContext(), "业务", Toast.LENGTH_SHORT).show();
        Button btn = baseRootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
