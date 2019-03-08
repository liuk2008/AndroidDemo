package com.android.common.webview.client;


import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.CookieManager;

import java.util.HashMap;
import java.util.Map;


/**
 * 对系统CookieManager进行Cookie的写入操作
 * 1、Android系统WebView是将cookie存储data/data/package_name/app_webview这个目录下的一个叫Cookies的数据中
 * 2、Cookie 同步方法要在 WebView 的 setting 设置完，loadUrl(url)调用前，进行Cookie操作，否则无效。
 * （TODO 待验证）
 * 3、添加header
 * 4、从Cookie中获取token
 */
public class WebViewUtils {

    private static Map<String, String> cookieMap = new HashMap<>();
    private static Map<String, String> headerMap = new HashMap<>();
    private static String token = "";

    public static void setCookie(@Nullable String key, @Nullable String value) {
        if (null == key) return;
        if (null == value) value = "";
        cookieMap.put(key, value);
    }

    public static void setHeader(@Nullable String key, @Nullable String value) {
        if (null == key) return;
        if (null == value) value = "";
        headerMap.put(key, value);
    }

    public static void clearCookie() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            CookieManager.getInstance().removeAllCookie();
        }
        cookieMap.clear();
    }

    public static void clearHeader() {
        headerMap.clear();
    }

    // 从cookie中获取token
    public static void setToken(String url) {
        if (TextUtils.isEmpty(url)) return;
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        if (!TextUtils.isEmpty(cookie)) {
            String[] splits = cookie.split(";");
            for (String split : splits) {
                split = split.trim();
                String[] tokens = split.split("=");
                if ("token".equals(tokens[0].trim().toLowerCase())) {
                    if (!token.equals(tokens[1].trim())) {
                        token = tokens[1].trim();
                    }
                }
            }
        }
    }

    public static String getToken() {
        return token;
    }

    public static Map<String, String> getCookieMap() {
        return cookieMap;
    }

    public static Map<String, String> getHeaderMap() {
        return headerMap;
    }

}
