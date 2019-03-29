package com.android.demo.netdemo.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.common.utils.common.LogUtils;
import com.android.demo.netdemo.AccountSummaryInfo;
import com.android.demo.netdemo.GeeValidateInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;

import com.android.network.Null;
import com.android.network.callback.Callback;
import com.android.network.http.request.HttpParams;
import com.android.network.http.request.HttpTask;
import com.android.network.http.request.HttpUtils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HttpDemo {

    private static final String TAG = HttpDemo.class.getSimpleName();
    private List<HttpTask> taskList = new ArrayList<>();

    public void login(Callback<UserInfo> callback) {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://passport.lawcert.com/proxy/account/user/login")
                .appendParams("phone", "18909131172")
                .appendParams("password", "123qwe")
                .builder();

        HttpTask task = HttpUtils.doPost(httpParams, callback);
        taskList.add(task);
    }

    public void checkPhone(Callback<LinkedHashMap<String, Object>> callback) {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://passport.lawcert.com/proxy/account/user/phone/exist/18909131172")
                .appendHeader("version", "1.0")
                .appendCookie("version","1.0")
                .builder();
        HttpTask task = HttpUtils.doGet(httpParams, callback);
        taskList.add(task);
    }

    // 获取极验码
    public void checkGeetest(Context context) {
        final GT3GeetestUtilsBind gt3GeetestUtilsBind = new GT3GeetestUtilsBind(context);
        gt3GeetestUtilsBind.getGeetest(context,
                "https://passport.lawcert.com/proxy/account/captcha/slip/",
                "https://passport.lawcert.com/proxy/account/validate/login/",
                "",
                new GT3GeetestBindListener() {
                    //开启自定义二次校验
                    @Override
                    public boolean gt3SetIsCustom() {
                        return true;
                    }

                    //自定义二次验证，当gtSetIsCustom为ture时执行这里面的代码
                    @Override
                    public void gt3GetDialogResult(boolean status, String result) {
                        if (status) {
                            if (TextUtils.isEmpty(result)) {
                                gt3GeetestUtilsBind.gt3TestClose();
                            } else {
                                gt3GeetestUtilsBind.gt3TestFinish();
                                Gson gson = new Gson();
                                GeeValidateInfo model = gson.fromJson(result, GeeValidateInfo.class);
                                sendPwdSms(model);
                            }
                        } else {
                            gt3GeetestUtilsBind.gt3TestClose();
                        }
                    }
                });
    }

    private void sendPwdSms(GeeValidateInfo info) {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://passport.lawcert.com/proxy/account/sms/resetPassword/18909131173")
                .appendParams("gtServerStatus", info.getGtServerStatus())
                .appendParams("challenge", info.getGeetest_challenge())
                .appendParams("validate", info.getGeetest_validate())
                .appendParams("seccode", info.getGeetest_seccode())
                .appendParams("slipFlag", true)
                .appendParams("phone", "18909131173")
                .appendParams("validationType", "SMS")
                .builder();
        HttpTask task = HttpUtils.doPost(httpParams, new Callback<Null>() {
            @Override
            public void onSuccess(Null mNull) {
                LogUtils.logd(TAG, "Null :" + mNull);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
        taskList.add(task);
    }

    public void monthBill(Callback<MonthBillInfo> callback) {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://www.lawcert.com/proxy/hzcms/channelPage/monthBill")
                .builder();
        HttpTask task = HttpUtils.doGet(httpParams, callback);
        taskList.add(task);
    }

    public void accountSummary(Callback<AccountSummaryInfo> callback) {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://www.lawcert.com/proxy/trc_bjcg/u/m/myAccount/accountSummary")
                .builder();
        HttpTask task = HttpUtils.doGet(httpParams, callback);
        taskList.add(task);
    }


    public void cancelTask(HttpTask task) {
        taskList.remove(task);
        HttpUtils.cancelTask(task);
    }

    public void cancelAll() {
        HttpUtils.cancelAll(taskList);
    }

}
