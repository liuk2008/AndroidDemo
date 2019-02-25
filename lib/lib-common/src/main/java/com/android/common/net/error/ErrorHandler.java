package com.android.common.net.error;

import com.android.common.net.NetConstant;
import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 网络请求异常处理：
 * <p>
 * 1、处理网络层异常时
 * 2、处理通过网络层抛出的业务层异常
 * 3、处理业务层异常
 */
public class ErrorHandler {

    public static ErrorData handlerError(Throwable throwable) {
        ErrorData data = new ErrorData();
        data.setData(throwable.getMessage());
        if (throwable instanceof TimeoutException
                || throwable instanceof SocketTimeoutException) {
            data.setCode(NetConstant.ERROR_TIMEOUTXCEPTION);
            data.setMsg("网络连接超时");
        } else if (throwable instanceof JsonSyntaxException) {
            data.setCode(NetConstant.ERROR_JSONEXCEPTION);
            data.setMsg("Json转换错误");
        } else if (throwable instanceof ErrorException) { // 业务异常
            data.setCode(((ErrorException) throwable).getCode());
            data.setMsg(throwable.getMessage());
        } else if (throwable instanceof HttpException) { // 网络错误
            data.setCode(NetConstant.ERROR_NETWORKEXCEPTION);
            data.setMsg("网络异常");
            try {
                // 业务层异常通过网络层抛出时，特殊处理
                HttpException httpEx = (HttpException) throwable;
                Response response = httpEx.response();
                ResponseBody responseBody = response.errorBody();
                if (responseBody != null) {
                    String result = new String(responseBody.bytes());
                    data.setData(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data.setCode(NetConstant.ERROR_OTHER);
            data.setMsg("服务器连接异常");
            throwable.printStackTrace();
        }
        return data;
    }
}
