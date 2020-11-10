package com.android.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 当你有一个activity，想让这个activity根据事件响应可以对应不同的界面时，就可以创建几个fragment，将fragment绑定到该activity
 * BaseFragment：
 * 声明跟View相关的方法
 */
public class BaseFragment extends CoreFragment implements View.OnClickListener {

    public String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;

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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLog("onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLog("onActivityCreated");
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
        showLog("onViewStateRestored");
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

    public void showLog(String methodName) {
        Log.d(TAG, "showLog: " + methodName);
    }

    /**
     * 设置Fragment布局文件
     *
     * @param inflater
     * @param layoutId
     * @return
     */
    public View setRootView(LayoutInflater inflater, int layoutId) {
        if (mRootView == null) {
            mRootView = inflater.inflate(layoutId, null);
//            rootView = inflater.inflate(layoutId, container, false);
            initRootViews(mRootView);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    public void initRootViews(View baseRootView) {

    }

    public void afterCreateView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        if (null == view)
            return;
    }

}
