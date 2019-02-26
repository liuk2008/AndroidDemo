package com.android.demo.fragment;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.common.base.PermissionActivity;
import com.android.common.base.activity.CoreActivity;
import com.android.common.base.fragment.BaseFragment;
import com.android.common.net.callback.Callback;
import com.android.common.view.ToolbarUtil;
import com.android.demo.R;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;
import com.android.demo.netdemo.http.HttpDemo;
import com.android.demo.netdemo.retrofit.RetrofitDemo;
import com.android.demo.netdemo.rxjava.RxNetDemo;
import com.android.utils.common.LogUtils;
import com.android.utils.system.SystemUtils;
import com.viewinject.annotation.MyOnClick;
import com.viewinject.bindview.MyViewInjector;
import com.zxing.activity.CaptureActivity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
    }

    @MyOnClick(R.id.btn_permission)
    public void permission() {
        // 测试申请单个权限
        PermissionActivity.requestPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE, "图片等文件存储需要SD卡读写权限");
    }

    @MyOnClick(R.id.btn_permissions)
    public void permissions() {
        // 测试申请多个权限
        List<String> permissions = SystemUtils.getDeniedPermission(getActivity());
        if (permissions.size() > 0) {
            PermissionActivity.requestPermissions(getActivity(),
                    permissions.toArray(new String[permissions.size()]),
                    "请打开SD卡读写权限、拍照等系统权限");
        }
    }

    @MyOnClick(R.id.btn_scan)
    public void scan() {
        startActivity(new Intent(getActivity(), CaptureActivity.class));
    }

    HttpDemo httpDemo;
    RetrofitDemo retrofitDemo;
    RxNetDemo rxNetDemo;

    @MyOnClick(R.id.btn_net)
    public void net() {
        httpDemo = new HttpDemo();
        retrofitDemo = new RetrofitDemo();
        rxNetDemo = new RxNetDemo();
        test();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyViewInjector.unbindView(this);
        rxNetDemo.cancelAll();
        httpDemo.cancelAll();
        retrofitDemo.cancelAll();
    }

    private void test() {
        rxNetDemo.login(new Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userinfo :" + userInfo);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
        httpDemo.checkPhone(new Callback<LinkedHashMap<String, Object>>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> linkedHashMap) {
                Set<String> keys = linkedHashMap.keySet();
                for (String key : keys) {
                    LogUtils.logd(TAG, "key :" + key);
                    LogUtils.logd(TAG, "value :" + linkedHashMap.get(key));
                }
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
        retrofitDemo.monthBill(new Callback<MonthBillInfo>() {
            @Override
            public void onSuccess(MonthBillInfo info) {
                LogUtils.logd(TAG, "info :" + info);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }


}
