package com.android.demo.netdemo.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.common.net.Null;
import com.android.common.net.callback.Callback;
import com.android.common.net.http.request.HttpParams;
import com.android.common.net.http.request.HttpUtils;
import com.android.demo.netdemo.GeeValidateInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;
import com.android.utils.common.LogUtils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Set;

public class HttpDemo {

    private static final String TAG = HttpDemo.class.getSimpleName();

    public static void login() {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://passport.lawcert.com/proxy/account/user/login")
                .appendParams("phone", "18909131172")
                .appendParams("password", "123qwe")
                .builder();
        HttpUtils.doPost(httpParams, new Callback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo info) {
                LogUtils.logd(TAG, "info :" + info);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    public static void checkPhone() {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://passport.lawcert.com/proxy/account/user/phone/exist/18909131190")
                .builder();
        HttpUtils.doGet(httpParams, new Callback<LinkedHashMap<String, Object>>() {
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


    // 获取极验码
    public static void checkGeetest(Context context) {
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

    private static void sendPwdSms(GeeValidateInfo info) {
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
        HttpUtils.doPost(httpParams, new Callback<Null>() {
            @Override
            public void onSuccess(Null mNull) {
                LogUtils.logd(TAG, "Null :" + mNull);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    public static void monthBill() {
        HttpParams httpParams = new HttpParams.Builder()
                .setUrl("https://www.lawcert.com/proxy/hzcms/channelPage/monthBill")
                .builder();

        HttpUtils.doGet(httpParams, new Callback<MonthBillInfo>() {
            @Override
            public void onSuccess(MonthBillInfo info) {
                LogUtils.logd(TAG, "info :" + info);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });

    }


}
