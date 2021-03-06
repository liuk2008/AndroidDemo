package com.android.demo.mvp.model.retrofit;


import com.android.demo.mvp.entity.AccountSummaryInfo;
import com.android.demo.mvp.entity.FinanceListInfo;
import com.android.demo.mvp.entity.GeeValidateInfo;
import com.android.demo.mvp.entity.MonthBillInfo;
import com.android.demo.mvp.entity.UserInfo;
import com.android.network.Null;
import com.android.network.retrofit.RetrofitEngine;
import com.android.network.utils.NetworkStatus;

import java.util.LinkedHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

public class RetrofitApi {

    private static final String TAG = RetrofitApi.class.getSimpleName();
    private static RetrofitApi retrofitApiRequest;
    private RetrofitService accountApi, financeApi;
    private RetrofitEngine retrofitEngine;

    private RetrofitApi() {
        retrofitEngine = RetrofitEngine.getInstance();
        accountApi = retrofitEngine.getRetrofitService(RetrofitService.class,
                "https://passport.lawcert.com/proxy/account/",
                NetworkStatus.Type.RETROFIT_DEFAULT);
        financeApi = retrofitEngine.getRetrofitService(RetrofitService.class,
                "https://www.lawcert.com/proxy/",
                NetworkStatus.Type.RETROFIT_DATAWRAPPER);
    }

    public static RetrofitApi getInstance() {
        if (retrofitApiRequest == null) {
            retrofitApiRequest = new RetrofitApi();
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
