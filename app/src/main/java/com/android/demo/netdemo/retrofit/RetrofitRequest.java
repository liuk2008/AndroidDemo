package com.android.demo.netdemo.retrofit;


import com.android.common.net.NetStatus;
import com.android.common.net.Null;
import com.android.common.net.retrofit.RetrofitEngine;
import com.android.demo.netdemo.AccountSummaryInfo;
import com.android.demo.netdemo.FinanceListInfo;
import com.android.demo.netdemo.GeeValidateInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;

import java.util.LinkedHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

public class RetrofitRequest {

    private static final String TAG = RetrofitRequest.class.getSimpleName();
    private static RetrofitRequest retrofitApiRequest;
    private FinanceApi accountApi, financeApi;
    private RetrofitEngine retrofitEngine;

    private RetrofitRequest() {
        retrofitEngine = RetrofitEngine.getInstance();
        accountApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://passport.lawcert.com/proxy/account/",
                NetStatus.Type.RETROFIT_DEFAULT);
        financeApi = retrofitEngine.getRetrofitService(FinanceApi.class,
                "https://www.lawcert.com/proxy/",
                NetStatus.Type.RETROFIT_DEFAULT_DATAWRAPPER);
    }

    public static RetrofitRequest getInstance() {
        if (retrofitApiRequest == null) {
            retrofitApiRequest = new RetrofitRequest();
        }
        return retrofitApiRequest;
    }

    public Call<UserInfo> login(String phone, String pwd) {
        return accountApi.login(phone, pwd);
    }

    public Call<LinkedHashMap<String, Object>> checkPhone(String phone) {
        return accountApi.checkPhone(phone);
    }

    public Call<LinkedHashMap<String, Object>> sendLoginSms(String phone, GeeValidateInfo info) {
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

    public Call<Null> sendPwdSms(boolean slipFlag,
                                 String challenge,
                                 String validate,
                                 String seccode,
                                 int gtServerStatus,
                                 String phone) {
        return accountApi.sendPwdSms(slipFlag, challenge, validate, seccode, gtServerStatus, phone);
    }

    public Call<MonthBillInfo> monthBill() {
        return financeApi.monthBill();
    }

    public Call<AccountSummaryInfo> accountSummary() {
        return financeApi.accountSummary();
    }

    public Call<FinanceListInfo> financeList(int pageIndex) {
        return financeApi.financeList(pageIndex, 40, "app");
    }

}
