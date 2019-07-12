package com.android.demo.mvp.model.rxjava;


import com.android.demo.mvp.entity.AccountSummaryInfo;
import com.android.demo.mvp.entity.GeeValidateInfo;
import com.android.demo.mvp.entity.MonthBillInfo;
import com.android.demo.mvp.entity.UserInfo;
import com.android.network.Null;
import com.android.network.network.NetworkStatus;
import com.android.network.retrofit.RetrofitEngine;

import java.util.LinkedHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class RxNetApi {

    private static final String TAG = RxNetApi.class.getSimpleName();
    private static RxNetApi retrofitApiRequest;
    private RxNetService accountApi, financeApi;
    private RetrofitEngine retrofitEngine;

    private RxNetApi() {
        retrofitEngine = RetrofitEngine.getInstance();
        accountApi = retrofitEngine.getRetrofitService(RxNetService.class,
                "https://passport.lawcert.com/proxy/account/",
                NetworkStatus.Type.RETROFIT_RXJAVA);
        financeApi = retrofitEngine.getRetrofitService(RxNetService.class,
                "https://www.lawcert.com/proxy/",
                NetworkStatus.Type.RETROFIT_RXJAVA_DATAWRAPPER);
    }

    public static RxNetApi getInstance() {
        if (retrofitApiRequest == null) {
            retrofitApiRequest = new RxNetApi();
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

    public Observable<AccountSummaryInfo> accountSummary() {
        return financeApi.accountSummary();
    }

}
