package com.android.demo.mvp.presenter.presenter;

import com.android.common.utils.common.LogUtils;
import com.android.demo.mvp.entity.UserInfo;
import com.android.demo.mvp.model.http.HttpApi;
import com.android.demo.mvp.presenter.BasePresenter;
import com.android.demo.mvp.presenter.BaseResult;
import com.android.network.callback.Callback;
import com.android.network.http.request.HttpTask;
import com.android.network.http.request.HttpUtils;

import java.util.LinkedHashMap;

/**
 * 使用 AsyncTask + Callback 进行请求
 * 处理内存泄露问题：
 * 1、中断View层与Presenter层强引用 / 使用弱引用WearReference进行处理
 * 2、页面销毁时统一取消网络回调
 * Created by Administrator on 2018/2/1.
 */

public class LoginPresenterImpl implements BasePresenter.LoginPresenter {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName();
    private BaseResult.LoginResult loginResult;
    private BaseResult.CheckPhoneResult checkPhoneResult;
    private HttpTask loginTask,phoneTask;

    public LoginPresenterImpl(BaseResult.LoginResult loginResult,
                              BaseResult.CheckPhoneResult checkPhoneResult) {
        this.loginResult = loginResult;
        this.checkPhoneResult = checkPhoneResult;
    }

    // 关联网络层与业务层
    @Override
    public void login() {
        // 调用网络层
        loginTask = HttpApi.login(new Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                // 关联业务层
                if (loginResult != null)
                    loginResult.loginSuccess(userInfo);
                else
                    LogUtils.logd(TAG, "onSuccess: 页面销毁");
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                if (loginResult != null)
                    loginResult.loginFail(resultCode, msg);
                else
                    LogUtils.logd(TAG, "onFail: 页面销毁");
            }
        });
    }

    @Override
    public void checkPhone() {
        phoneTask = HttpApi.checkPhone(new Callback<LinkedHashMap<String, Object>>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> linkedHashMap) {
                if (checkPhoneResult != null)
                    checkPhoneResult.checkPhoneSuccess(linkedHashMap);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                if (checkPhoneResult != null)
                    checkPhoneResult.checkPhoneFail(resultCode, msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        // 取消网络请求
        HttpUtils.cancelTask(loginTask);
        HttpUtils.cancelTask(phoneTask);
        // 中断View层强引用
        loginResult = null;
        checkPhoneResult = null;
    }

}
