package com.android.demo.netdemo.retrofit;



import com.android.demo.netdemo.AccountSummaryInfo;
import com.android.demo.netdemo.FinanceListInfo;
import com.android.demo.netdemo.MonthBillInfo;
import com.android.demo.netdemo.UserInfo;
import com.android.network.Null;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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

    @GET("hzcms/channelPage/monthBill")
    Call<MonthBillInfo> monthBill();

    @GET("trc_bjcg/u/m/myAccount/accountSummary")
    Call<AccountSummaryInfo> accountSummary();

    @GET("trc_bjcg/loans/list")
    Call<FinanceListInfo> financeList(@Query("pageIndex") int pageIndex,
                                      @Query("pageSize") int pageSize,
                                      @Query("displayTerminal") String displayTerminal);

    /*
     * 设置请求接口
     * @QueryMap注解会把参数拼接到url后面，所以它适用于GET请求；
     * @Body会把参数放到请求体中，所以适用于POST请求。
     */

    @GET("qingqi/product/login")
    Call<ResponseBody> loginGet(@QueryMap Map<String, Object> hashMap);

    @POST("api/hongyan/serverstation/login")
    Call<ResponseBody> loginPost(@Body RequestBody requestBody);


}

