package com.android.demo.mvp.model.retrofit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.common.utils.common.LogUtils;
import com.android.demo.mvp.entity.AccountSummaryInfo;
import com.android.demo.mvp.entity.FinanceListInfo;
import com.android.demo.mvp.entity.GeeValidateInfo;
import com.android.demo.mvp.entity.MonthBillInfo;
import com.android.demo.mvp.entity.UserInfo;
import com.android.network.Null;
import com.android.network.callback.Callback;
import com.android.network.retrofit.RetrofitUtils;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;

public class RetrofitDemo {

    private static final String TAG = RetrofitDemo.class.getSimpleName();
    private static RetrofitApi apiRequest = RetrofitApi.getInstance();
    private List<Call> callList = new ArrayList<>();

    public void login(Callback<UserInfo> callback) {
        Call<UserInfo> call = apiRequest.login("18909131172", "123qwe");
        RetrofitUtils.request(call, callback);
        callList.add(call);
    }

    public void checkPhone(Callback<LinkedHashMap<String, Object>> callback) {
        Call<LinkedHashMap<String, Object>> call = apiRequest.checkPhone("18909131173");
        RetrofitUtils.request(call, callback);
        callList.add(call);
    }

    // 获取极验码
    public void checkGeetest(Context context, final String phone) {
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
                                sendPwdSms(phone, model);
                            }
                        } else {
                            gt3GeetestUtilsBind.gt3TestClose();
                        }
                    }
                });
    }

    public void sendLoginSms(String phone, GeeValidateInfo model) {
        Call<LinkedHashMap<String, Object>> call = apiRequest.sendLoginSms("18909131189", model);
        RetrofitUtils.request(call, new Callback<LinkedHashMap<String, Object>>() {
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

    public void sendPwdSms(String phone, GeeValidateInfo model) {
        Call<Null> call = apiRequest.sendPwdSms(true,
                model.getGeetest_challenge(),
                model.getGeetest_validate(),
                model.getGeetest_seccode(),
                model.getGtServerStatus(),
                phone);
        RetrofitUtils.request(call, new Callback<Null>() {
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

    public void monthBill(Callback<MonthBillInfo> callback) {
        Call<MonthBillInfo> call = apiRequest.monthBill();
        RetrofitUtils.request(call, callback);
        callList.add(call);
    }

    public void accountSummary(Callback<AccountSummaryInfo> callback) {
        Call<AccountSummaryInfo> call = apiRequest.accountSummary();
        RetrofitUtils.request(call, callback);
        callList.add(call);
    }

    public void financeList(int pageIndex, Callback<FinanceListInfo> callback) {
        Call<FinanceListInfo> call = apiRequest.financeList(pageIndex);
        RetrofitUtils.request(call, callback);
        callList.add(call);
    }

    public void cancelTask(Call call) {
        callList.remove(call);
        RetrofitUtils.cancelTask(call);
    }

    public void cancelAll() {
        Log.d(TAG, "cancelAll: " + callList.size());
        RetrofitUtils.cancelAll(callList);
    }

}
