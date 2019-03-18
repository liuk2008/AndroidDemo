package com.android.common.net.http.engine;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.common.BuildConfig;
import com.android.common.net.NetStatus;
import com.android.common.utils.NetUtils;
import com.android.common.net.http.request.HttpParams;
import com.android.common.net.http.request.NetData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * http网络核心层
 * <p>
 * * 关于要不要显示调用connect方法的问题：
 * * 1、不需要显示调用connect方法
 * * 2、必须调用getResponseCode()方法
 * * 在不调用getResponseCode()方法的时候，无论是否调用connect()方法，请求都是不能成功的，调用connect()方法只是建立连接，并不会向服务器传递数据，
 * * 只用调用getResponseCode()方法时，才会向服务器传递数据(有博文说是getInputStream()才会向服务器传递数据，getResponseCode中会调用getInputStream方法)。
 * * 跟着getResponseCode()源码发现里面调用了getInputStream()方法，在getInputStream()方法中会判断当前是否连接，如果没有连接，则调用connect()方法建立连接。
 */
public class HttpEngine {
    private final static String TAG = "http";
    private final static String ENCODE_TYPE = "utf-8";

    private static HttpEngine httpEngine;
    private Context mContext;
    private HttpParams httpParams;

    public static synchronized HttpEngine getInstance() {
        if (httpEngine == null) {
            httpEngine = new HttpEngine();
        }
        return httpEngine;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void setHttpParams(HttpParams httpParams) {
        this.httpParams = httpParams;
    }

    /**
     * 检测网络状态
     * 1、是否连接网络
     * 2、已连接网络，是否可正常访问网络
     */
    private NetData checkNet() {
        if (null == mContext)
            throw new RuntimeException("未初始化 HttpEngine");
        boolean isConnected = NetUtils.isNetConnected(mContext);
        if (!isConnected) {
            NetData data = new NetData(NetStatus.NETWORK_DISCONNECTED.getErrorCode(),
                    NetStatus.NETWORK_DISCONNECTED.getErrorMessage(), "");
            NetUtils.showToast(mContext, NetStatus.NETWORK_DISCONNECTED.getErrorMessage());
            return data;
        }
        boolean isValidated = NetUtils.isNetValidated(mContext);
        if (!isValidated) {
            NetData data = new NetData(NetStatus.NETWORK_UNABLE.getErrorCode(),
                    NetStatus.NETWORK_UNABLE.getErrorMessage(), "");
            NetUtils.showToast(mContext, NetStatus.NETWORK_UNABLE.getErrorMessage());
            return data;
        }
        return null;
    }

    /**
     * GET 请求
     * 请求参数赋值在url
     */
    public NetData doGet() throws Exception {
        NetData data = checkNet();
        if (null != data) return data;
        String url = httpParams.url;
        String params = addParameter(httpParams.params);
        if (!TextUtils.isEmpty(params)) {
            url = url + "?" + params;
        }
        return request(url, "", "GET");
    }

    /**
     * POST 请求
     * 提交表单数据需要编码
     * 提交json数据不需要编码
     */
    public NetData doPost() throws Exception {
        NetData data = checkNet();
        if (null != data) return data;
        String url = httpParams.url;
        String params = "";
        if ("application/json".equals(httpParams.contentType)) {
            if (httpParams.params != null) {
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                params = gson.toJson(httpParams.params);
            }
        } else {
            params = addParameter(httpParams.params);
        }
        return request(url, params, "POST");
    }

    public NetData request(String url, String params, String requestMethod) throws Exception {
        HttpURLConnection connection = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        PrintWriter out = null;
        int responseCode = -1;
        String msg = "服务器访问异常";
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 设置release版本禁止通过代理抓取http、https请求
            if ("release".equals(BuildConfig.BUILD_TYPE)) {
                connection = (HttpURLConnection) realUrl.openConnection(Proxy.NO_PROXY);
            } else {
                connection = (HttpURLConnection) realUrl.openConnection();
            }
            // 设置连接主机超时
            connection.setConnectTimeout(NetStatus.Type.TIMEOUT_MILLISECONDS);
            // 设置从主机读取数据超时
            connection.setReadTimeout(NetStatus.Type.TIMEOUT_MILLISECONDS);
            // 设置请求方式
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Charset", ENCODE_TYPE);
            connection.setRequestProperty("Connection", "keep-alive");
            if (!TextUtils.isEmpty(httpParams.contentType)) {
                connection.setRequestProperty("Content-Type", httpParams.contentType);
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            connection.setRequestProperty("Response-Type", "json");
            // 设置自定义请求头
            Map<String, String> headers = httpParams.headers;
            for (String header : headers.keySet()) {
                connection.setRequestProperty(header, headers.get(header));
            }
            // 设置cookie
            connection.setRequestProperty("Cookie", httpParams.setCookie(mContext));
            Log.d(TAG, "request: url = [" + url + "]");
            Log.d(TAG, "request: method = [" + requestMethod + "]");
            Log.d(TAG, "request: params = [" + params + "]");
            Map<String, List<String>> requestProperties = connection.getRequestProperties();
            for (String key : requestProperties.keySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append("request: " + key + " = ");
                List<String> values = requestProperties.get(key);
                if (values != null && values.size() > 0) {
                    for (String value : values) {
                        sb.append("[" + value + "]");
                    }
                }
                Log.d(TAG, sb.toString());
            }
            // 设置POST方式
            if ("POST".equals(requestMethod)) {
                // Post请求不能使用缓存
                connection.setUseCaches(false);
                // 发送POST请求必须设置如下两行
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // 设置参数
                if (!TextUtils.isEmpty(params) && !"".equals(params.trim())) {
                    // 获取URLConnection对象对应的输出流
                    out = new PrintWriter(connection.getOutputStream());
                    // 发送请求参数
                    out.print(params);
                    // flush 输出流的缓冲
                    out.flush();
                }
            }
            // 调用connect()只是建立连接，并不会向服务器传送数据，只要调用getResponseCode()，就不必要调用connect方法
            connection.connect();
            // 获得服务器响应的结果和状态码
            responseCode = connection.getResponseCode();
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            for (String key : headerFields.keySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(TextUtils.isEmpty(key) ? "response: " : "response: " + key + " = ");
                List<String> values = headerFields.get(key);
                if (values != null && values.size() > 0) {
                    for (String value : values) {
                        sb.append("[" + value + "]");
                    }
                    // 存储cookie
                    if ("Set-Cookie".equals(key)) {
                        httpParams.getCookie(mContext, values);
                    }
                }
                Log.d(TAG, sb.toString());
            }
            if (responseCode == 200) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                result = baos.toString();
                msg = "网络正常";
            } else {
                is = connection.getErrorStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                result = baos.toString();
            }
        } finally { // 关闭资源
            try {
                if (is != null)
                    is.close();
                if (baos != null)
                    baos.close();
                if (connection != null)
                    connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "response: result = [" + result + "]");
        NetData data = new NetData(responseCode, msg, result);
        return data;
    }

    // 拼接参数列表
    private String addParameter(Map<String, Object> paramsMap) {
        String paramStr = "";
        if (paramsMap != null && paramsMap.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String key : paramsMap.keySet()) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                try {
                    String value = String.valueOf(paramsMap.get(key));
                    stringBuilder.append(URLEncoder.encode(value, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
                stringBuilder.append("&");
            }
            paramStr = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return paramStr;
    }


}