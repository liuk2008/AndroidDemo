package com.android.demo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 当你有一个activity，想让这个activity根据事件响应可以对应不同的界面时，就可以创建几个fragment，将fragment绑定到该activity
 */
public class BaseFragment extends CoreFragment {

    protected String BASETAG = BaseFragment.class.getSimpleName();
    private View baseRootView;
    private int layoutId = -1;

    public BaseFragment() {
        showLog("BaseFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showLog("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLog("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showLog("onCreateView");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLog("onActivityCreated");
        afterCreateView(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        showLog("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        showLog("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        showLog("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showLog("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showLog("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showLog("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLog("onDetach");
    }

    private void showLog(String methodName) {
        Log.d(BASETAG, methodName);
    }


    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    protected void initRootViews(View baseRootView) {

    }

    protected void afterCreateView(@Nullable Bundle savedInstanceState) {

    }

}
