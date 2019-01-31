package com.android.common.base.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.R;
import com.android.utils.system.NetUtils;

/**
 * 当你有一个activity，想让这个activity根据事件响应可以对应不同的界面时，就可以创建几个fragment，将fragment绑定到该activity
 * BaseFragment：
 * 声明跟View相关的方法
 */
public class BaseFragment extends CoreFragment implements View.OnClickListener {

    public String BASETAG = BaseFragment.class.getSimpleName();
    private View baseRootView;
    private int layoutId = -1;
    private BroadcastReceiver netReceiver;

    public BaseFragment() {
        showLog(BASETAG, "BaseFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showLog(BASETAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog(BASETAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showLog(BASETAG, "onCreateView");
        if (layoutId != -1) {
            if (baseRootView == null) {
                baseRootView = inflater.inflate(layoutId, null);
//                 rootView = inflater.inflate(layoutId, container, false);
                initRootViews(baseRootView);
            }
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) baseRootView.getParent();
            if (parent != null) {
                parent.removeView(baseRootView);
            }
            return baseRootView;
        } else
            return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLog(BASETAG, "onViewCreated");
        checkNet(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLog(BASETAG, "onActivityCreated");
        afterCreateView(savedInstanceState);
    }

    /**
     * Fragment中EditText文字的记忆性
     * 当fragment已存在时，重新加载会执行onViewStateRestored把原有的控件数据重新赋值回来。
     * onViewStateRestored在onActivityCreated(Bundle)后面执行，所以onViewCreated里面的mobileEt被覆盖掉了。
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        showLog(BASETAG, "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        showLog(BASETAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLog(BASETAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        showLog(BASETAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showLog(BASETAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showLog(BASETAG, "onDestroyView");
        if (null != netReceiver) {
            getActivity().unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showLog(BASETAG, "onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLog(BASETAG, "onDetach");
    }


    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void initRootViews(View baseRootView) {

    }

    public void afterCreateView(@Nullable Bundle savedInstanceState) {

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
        if (null != baseRootView) {
            for (int id : ids) {
                View view = baseRootView.findViewById(id);
                setViewStatus(view, View.VISIBLE);
            }
        }
    }

    public void setInvisible(View... views) {
        for (View view : views) {
            setViewStatus(view, View.INVISIBLE);
        }
    }

    public void setInvisible(int... ids) {
        if (null != baseRootView) {
            for (int id : ids) {
                View view = baseRootView.findViewById(id);
                setViewStatus(view, View.INVISIBLE);
            }
        }
    }

    public void setGone(View... views) {
        for (View view : views) {
            setViewStatus(view, View.GONE);
        }
    }

    public void setGone(int... ids) {
        if (null != baseRootView) {
            for (int id : ids) {
                View view = baseRootView.findViewById(id);
                setViewStatus(view, View.GONE);
            }
        }
    }

    private void setViewStatus(View view, int type) {
        if (null != view && view.getVisibility() != type)
            view.setVisibility(type);
    }


    public void setText(@IdRes int textViewId, @Nullable Object text) {
        if (null != baseRootView) {
            TextView textView = baseRootView.findViewById(textViewId);
            setText(textView, text);
        }
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

    public void setOnClickListener(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void setOnClickListener(int... ids) {
        if (null != baseRootView) {
            for (int id : ids) {
                View view = baseRootView.findViewById(id);
                if (null != view)
                    view.setOnClickListener(this);
            }
        }
    }

    public View getRootView() {
        return baseRootView;
    }

    public void checkNet(final View view) {
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isConnected = NetUtils.isNetConnected(context);
                View net_tips = view.findViewById(R.id.tv_net_tips);
                if (null != net_tips) {
                    net_tips.setVisibility(isConnected ? View.GONE : View.VISIBLE);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(netReceiver, filter);
    }


}
