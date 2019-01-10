package com.android.demo.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.R;
import com.android.demo.account.AccountModPwdFragment;
import com.android.demo.base.BaseFragment;
import com.android.demo.base.FragmentHostActivity;


public class MineFragment extends BaseFragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    public MineFragment() {
        super();
        BASETAG = TAG;
        setLayoutId(R.layout.fragment_mine);
    }

    @Override
    protected void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        TextView textView = baseRootView.findViewById(R.id.textview);
        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
        Button btn = baseRootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentHostActivity.openFragment(getActivity(), AccountModPwdFragment.newInstance());
            }
        });
    }

}
