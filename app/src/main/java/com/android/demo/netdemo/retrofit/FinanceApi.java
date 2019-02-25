package com.android.demo.netdemo.retrofit;


import com.android.common.net.Null;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;

import java.util.LinkedHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinanceApi {

    // 账号登录
    @FormUrlEncoded
    @POST("user/login")
    Call<UserInfo> login(@Field("phone") String phone, @Field("password") String password);


    // 检查手机号是否存在
    @GET("user/phone/exist/{phone}")
    Call<LinkedHashMap<String, Object>> checkPhone(@Path("phone") String phone);

    // 发送快速登录&注册短信验证码
    @POST("validate/login/{phone}")
    Call<LinkedHashMap<String, Object>> sendLoginSms(@Body RequestBody requestBody, @Path("phone") String phone);

    // 获取重置登陆密码的短信
    @FormUrlEncoded
    @POST("sms/resetPassword/{phone}")
    Call<Null> sendPwdSms(@Field("slipFlag") boolean slipFlag,
                          @Field("challenge") String challenge,
                          @Field("validate") String validate,
                          @Field("seccode") String seccode,
                          @Field("gtServerStatus") int gtServerStatus,
                          @Path("phone") String phone);

    @GET("channelPage/monthBill")
    Call<MonthBillInfo> monthBill();
}

