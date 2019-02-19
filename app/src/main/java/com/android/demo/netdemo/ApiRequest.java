package com.android.demo.netdemo;


import com.android.common.net.NetConstant;
import com.android.common.net.Null;
import com.android.common.net.retrofit.RetrofitEngine;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ApiRequest {

    private static final String TAG = ApiRequest.class.getSimpleName();
    private static ApiRequest retrofitApiRequest;
    private FinanceApi accountApi, financeApi;
    private RetrofitEngine retrofitEngine;

    private ApiRequest() {
        retrofitEngine = RetrofitEngine.getInstance();
        accountApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://passport.lawcert.com/proxy/account/",
                NetConstant.RETROFIT_RXJAVA);
        financeApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://www.lawcert.com/proxy/hzcms/",
                NetConstant.RETROFIT_RXJAVA_DATAWRAPPER);
    }

    public static ApiRequest getInstance() {
        if (retrofitApiRequest == null) {
            retrofitApiRequest = new ApiRequest();
        }
        return retrofitApiRequest;
    }

    public Observable<UserInfo> login(String phone, String pwd) {
        return accountApi.login(phone, pwd);
    }

    public Observable<LinkedHashMap<String, Object>> checkPhone(String phone) {
        return accountApi.checkPhone(phone);
    }

    public Observable<LinkedHashMap<String, Object>> sendLoginSms(String phone, AccountGeeValidateInfo info) {
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

    public Observable<LinkedHashMap<String, Object>> monthBill() {
        return financeApi.monthBill();
    }
}
