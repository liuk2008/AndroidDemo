package com.android.demo.netdemo.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.common.net.Null;
import com.android.common.net.callback.Callback;
import com.android.common.net.retrofit.RetrofitUtils;
import com.android.demo.netdemo.GeeValidateInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;
import com.android.utils.common.LogUtils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;

public class RetrofitDemo {

    private static final String TAG = RetrofitDemo.class.getSimpleName();
    private static RetrofitRequest apiRequest = RetrofitRequest.getInstance();
    private List<Call> taskList = new ArrayList<>();

    public void login(Callback<UserInfo> callback) {
        Call<UserInfo> call = apiRequest.login("18909131172", "123qwe1");
        taskList.add(call);
        RetrofitUtils.request(call, callback);
    }


    public void checkPhone() {
        RetrofitUtils.request(apiRequest.checkPhone("18909131173"), new Callback<LinkedHashMap<String, Object>>() {
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
//                                sendLoginSms(model);
                                sendPwdSms(model);
                            }
                        } else {
                            gt3GeetestUtilsBind.gt3TestClose();
                        }
                    }
                });
    }

    public void sendLoginSms(GeeValidateInfo model) {
        RetrofitUtils.request(apiRequest.sendLoginSms("18909131189", model), new Callback<LinkedHashMap<String, Object>>() {
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

    public void sendPwdSms(GeeValidateInfo model) {
        RetrofitUtils.request(apiRequest.sendPwdSms(true,
                model.getGeetest_challenge(),
                model.getGeetest_validate(),
                model.getGeetest_seccode(),
                model.getGtServerStatus(),
                "18909131173"), new Callback<Null>() {
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

    public void monthBill() {
        RetrofitUtils.request(apiRequest.monthBill(), new Callback<MonthBillInfo>() {
            @Override
            public void onSuccess(MonthBillInfo info) {
                Log.d(TAG, "monthBillInfo: " + info);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);

            }
        });
    }

    public void cancelTask(Call call) {
        taskList.remove(call);
        RetrofitUtils.cancelTask(call);
    }

    public void cancelAll() {
        RetrofitUtils.cancelAll(taskList);
    }


}
