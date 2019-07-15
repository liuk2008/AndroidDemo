package com.android.demo.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.base.activity.BaseActivity;
import com.android.common.utils.common.LogUtils;
import com.android.common.utils.view.ViewUtils;
import com.android.demo.R;
import com.android.demo.mvp.entity.AccountSummaryInfo;
import com.android.demo.mvp.entity.MonthBillInfo;
import com.android.demo.mvp.entity.UserInfo;
import com.android.demo.mvp.model.retrofit.RetrofitDemo;
import com.android.demo.mvp.presenter.BasePresenter;
import com.android.demo.mvp.presenter.BaseResult;
import com.android.demo.mvp.presenter.presenter.LoginPresenterImpl;
import com.android.network.callback.Callback;
import com.viewinject.annotation.MyBindView;
import com.viewinject.bindview.MyViewInjector;

import java.util.LinkedHashMap;
import java.util.Set;

public class NetWorkActivity extends BaseActivity implements View.OnClickListener,
        BaseResult.LoginResult, BaseResult.CheckPhoneResult {

    private static final String TAG = NetWorkActivity.class.getSimpleName();
    private BasePresenter.LoginPresenter loginPresenter;

    @MyBindView(R.id.btn_login)
    public Button btnLogin;
    @MyBindView(R.id.btn_check)
    public Button btnCheck;

    private RetrofitDemo retrofitDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        MyViewInjector.bindView(this);

        ViewUtils.setOnClickListener(this, btnLogin, btnCheck);
        loginPresenter = new LoginPresenterImpl(this, this);

        retrofitDemo = new RetrofitDemo();
        testPhone();
        testBill();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                loginPresenter.login();
                break;
            case R.id.btn_check:
                loginPresenter.checkPhone();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyViewInjector.unbindView(this);
        loginPresenter.onDestroy();
        loginPresenter = null;
        retrofitDemo.cancelAll();
    }

    private void testLogin() {
        retrofitDemo.login(new Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userinfo :" + userInfo);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, LogUtils.getThreadName() + "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    private void testPhone() {
        retrofitDemo.checkPhone(new Callback<LinkedHashMap<String, Object>>() {
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

    private void testSummary() {
        retrofitDemo.accountSummary(new Callback<AccountSummaryInfo>() {
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

    @Override
    public void loginSuccess(UserInfo userInfo) {
        LogUtils.logd(TAG, "userInfo :" + userInfo);
    }

    @Override
    public void loginFail(int resultCode, String errorMsg) {

    }

    @Override
    public void checkPhoneSuccess(LinkedHashMap<String, Object> linkedHashMap) {
        Set<String> strings = linkedHashMap.keySet();
        for (String key : strings) {
            LogUtils.logd(TAG, "key:" + key);
            LogUtils.logd(TAG, "value:" + linkedHashMap.get(key));
        }
    }

    @Override
    public void checkPhoneFail(int resultCode, String errorMsg) {

    }

}
