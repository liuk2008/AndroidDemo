package com.android.demo.netdemo.rxjava;


import com.android.common.net.NetConstant;
import com.android.common.net.Null;
import com.android.common.net.retrofit.RetrofitEngine;
import com.android.demo.netdemo.GeeValidateInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class RxNetRequest {

    private static final String TAG = RxNetRequest.class.getSimpleName();
    private static RxNetRequest retrofitApiRequest;
    private FinanceApi accountApi, financeApi;
    private RetrofitEngine retrofitEngine;

    private RxNetRequest() {
        retrofitEngine = RetrofitEngine.getInstance();
        accountApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://passport.lawcert.com/proxy/account/",
                NetConstant.RETROFIT_RXJAVA);
        financeApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://www.lawcert.com/proxy/hzcms/",
                NetConstant.RETROFIT_RXJAVA_DATAWRAPPER);
    }

    public static RxNetRequest getInstance() {
        if (retrofitApiRequest == null) {
            retrofitApiRequest = new RxNetRequest();
        }
        return retrofitApiRequest;
    }

    public Observable<UserInfo> login(String phone, String pwd) {
        return accountApi.login(phone, pwd);
    }

    public Observable<LinkedHashMap<String, Object>> checkPhone(String phone) {
        return accountApi.checkPhone(phone);
    }

    public Observable<LinkedHashMap<String, Object>> sendLoginSms(String phone, GeeValidateInfo info) {
        RequestBody requestBody = retrofitEngine.newRequestBuilder()
                .append("gtServerStatus", info.getGtServerStatus())
                .append("challenge", info.getGeetest_challenge())
                .append("validate", info.getGeetest_validate())
                .append("seccode", info.getGeetest_seccode())
                .append("slipFlag", true)
                .append("phone", phone)
                .append("validationType", "SMS")
                .builder();
        return accountApi.sendLoginSms(requestBody, phone);
    }

    public Observable<Null> sendPwdSms(boolean slipFlag,
                                       String challenge,
                                       String validate,
                                       String seccode,
                                       int gtServerStatus,
                                       String phone) {
        return accountApi.sendPwdSms(slipFlag, challenge, validate, seccode, gtServerStatus, phone);
    }

    public Observable<MonthBillInfo> monthBill() {
        return financeApi.monthBill();
    }
}
