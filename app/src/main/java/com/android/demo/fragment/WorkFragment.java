package com.android.demo.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.base.fragment.BaseFragment;
import com.android.common.utils.common.LogUtils;
import com.android.common.utils.common.ToastUtils;
import com.android.common.utils.system.CacheUtils;
import com.android.common.utils.system.SystemUtils;
import com.android.common.utils.view.StatusBarUtils;
import com.android.common.utils.view.ToolbarUtil;
import com.android.common.utils.view.ViewUtils;
import com.android.common.widget.PermissionActivity;
import com.android.common.widget.photo.PhotoPickerActivity;
import com.android.demo.R;
import com.android.demo.WebViewActivity;
import com.android.demo.netdemo.AccountSummaryInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;
import com.android.demo.netdemo.http.HttpDemo;
import com.android.demo.netdemo.retrofit.RetrofitDemo;
import com.android.demo.netdemo.rxjava.RxNetDemo;
import com.android.network.callback.Callback;
import com.zxing.activity.CaptureActivity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 设置系统状态栏透明，通过设置自定义布局，保持颜色一致
 * 1、设置布局View，高度为0dp，背景色与主题色一致
 * 2、获取状态栏高度，设置布局View高度=状态栏高度
 */
public class WorkFragment extends BaseFragment {

    private static final String TAG = WorkFragment.class.getSimpleName();
    private HttpDemo httpDemo;
    private RetrofitDemo retrofitDemo;
    private RxNetDemo rxNetDemo;

    {
        super.TAG = TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return setRootView(inflater, R.layout.fragment_work);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        View statusBar = baseRootView.findViewById(R.id.status_bar_fix);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, StatusBarUtils.getStatusBarHeight(getActivity()));
        statusBar.setLayoutParams(layoutParams);
        ToolbarUtil.configTitle(baseRootView, "业务", View.GONE);

        ViewUtils.setViewOnClickListener(this, baseRootView, R.id.btn_permission, R.id.btn_permissions,
                R.id.btn_scan, R.id.btn_webview, R.id.btn_net, R.id.btn_cache, R.id.btn_photo);
        httpDemo = new HttpDemo();
        retrofitDemo = new RetrofitDemo();
        rxNetDemo = new RxNetDemo();

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_permission:
                // 测试申请单个权限
                PermissionActivity.requestPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, "图片等文件存储需要SD卡读写权限");
                break;
            case R.id.btn_permissions:
                // 测试申请多个权限
                List<String> permissions = SystemUtils.getDeniedPermission(getActivity());
                if (permissions.size() > 0) {
                    PermissionActivity.requestPermissions(getActivity(),
                            permissions.toArray(new String[permissions.size()]),
                            "请打开SD卡读写权限、拍照等系统权限");
                }
                break;
            case R.id.btn_scan:
                startActivity(new Intent(getActivity(), CaptureActivity.class));
                break;
            case R.id.btn_webview:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case R.id.btn_net:
                testBill();
                break;
            case R.id.btn_cache:
                CacheUtils.getCache(getActivity(), "com.android.demo", new CacheUtils.onCacheListener() {
                    @Override
                    public void onCache(long... size) {
                        StringBuilder cache = new StringBuilder();
                        cache.append(String.format("缓存大小：%s", Formatter.formatFileSize(getContext(), size[0])))
                                .append("\n")
                                .append(String.format("数据大小：%s", Formatter.formatFileSize(getContext(), size[1])))
                                .append("\n")
                                .append(String.format("应用大小：%s", Formatter.formatFileSize(getContext(), size[2])));
                        ToastUtils.showToast(getContext(), cache.toString());
                    }
                });
                break;
            case R.id.btn_photo:
                PhotoPickerActivity.requestPhoto(getActivity(), new PhotoPickerActivity.PhotoResultCallback() {
                    @Override
                    public void photoResult(String photoPath) {
                        LogUtils.logd(TAG, "photo:" + photoPath);
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rxNetDemo.cancelAll();
        httpDemo.cancelAll();
        retrofitDemo.cancelAll();
    }


    private void testLogin() {
        httpDemo.login(new Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userinfo :" + userInfo);
                testSummary();
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    private void testPhone() {
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
    }

    private void testBill() {
        httpDemo.monthBill(new Callback<MonthBillInfo>() {
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

    private void testSummary() {
        httpDemo.accountSummary(new Callback<AccountSummaryInfo>() {
            @Override
            public void onSuccess(AccountSummaryInfo info) {
                LogUtils.logd(TAG, "info :" + info);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }


}
