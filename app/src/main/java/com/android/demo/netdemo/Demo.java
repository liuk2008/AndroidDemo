package com.android.demo.netdemo;

import android.content.Context;
import android.text.TextUtils;

import com.android.common.net.Null;
import com.android.common.net.rxjava.RxHelper;
import com.android.common.net.rxjava.callback.Callback;
import com.android.common.net.rxjava.callback.Callback1;
import com.android.utils.common.LogUtils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class Demo {

    private static final String TAG = Demo.class.getSimpleName();
    private static ApiRequest apiRequest = ApiRequest.getInstance();

    public static void login() {
        RxHelper.subscribe(apiRequest.login("18909131172", "123qwe1"), new Callback1<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                LogUtils.logd(TAG, "userinfo :" + userInfo);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    public static void checkPhone() {
        RxHelper.subscribe(apiRequest.checkPhone("18909131173"), new Callback1<LinkedHashMap<String, Object>>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> linkedHashMap) {
                Set<String> keys = linkedHashMap.keySet();
                Collection<Object> values = linkedHashMap.values();
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
                                AccountGeeValidateInfo model = gson.fromJson(result, AccountGeeValidateInfo.class);
//                                sendLoginSms(model);
                                sendPwdSms(model);
                            }
                        } else {
                            gt3GeetestUtilsBind.gt3TestClose();
                        }
                    }
                });
    }

    public static void sendLoginSms(AccountGeeValidateInfo model) {
        RxHelper.subscribe(apiRequest.sendLoginSms("18909131189", model), new Callback1<LinkedHashMap<String, Object>>() {
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

    public static void sendPwdSms(AccountGeeValidateInfo model) {

        RxHelper.subscribe(apiRequest.sendPwdSms(true,
                model.getGeetest_challenge(),
                model.getGeetest_validate(),
                model.getGeetest_seccode(),
                model.getGtServerStatus(),
                "18909131173"), new Callback1<Null>() {
            @Override
            public void onSuccess(Null mNull) {
                LogUtils.logd(TAG, "mNull:" + mNull);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
            }
        });
    }

    public static void monthBill() {
        RxHelper.subscribe(apiRequest.monthBill(), new Callback<LinkedHashMap<String, Object>>() {
            @Override
            public void onSuccess(LinkedHashMap<String, Object> linkedHashMap) {
                Set<String> keys = linkedHashMap.keySet();
                for (String key : keys) {
                    LogUtils.logd(TAG, "key :" + key);
                    LogUtils.logd(TAG, "value :" + linkedHashMap.get(key));
                }
            }

            @Override
            public void onFail(int resultCode, String msg) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg);
            }
        });
    }
}
