package com.android.common.net.retrofit.interceptor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 设置Retrofit请求cookie作用域
 * TODO 待验证
 */
public class MyHttpCookieInterceptor implements CookieJar {

    private static final String TOKEN_KEY = "token";
    private static String mServerHost = "https://www.lawcert.com";
    private static Map<String, List<Cookie>> cookieMap = new HashMap<>();
    private Map<String, Cookie> tokenCookieMap = new HashMap();
    private String token;

    public static void setServerHost(String host) {
        mServerHost = host;
    }

    // 设置RequestHeader中的cookie
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> list = cookieMap.get(url.host());
        List<Cookie> cookieList = new ArrayList<>(1);
        if (null != list) cookieList.addAll(list);
        //  判断是否存在token
        if (token != null) {
            Cookie tokenCookie = null;
            for (Cookie cookie : cookieList) {
                if (TOKEN_KEY.equals(cookie.name())) {
                    tokenCookie = cookie;
                    break;
                }
            }
            if (tokenCookie == null || !tokenCookie.value().equals(token)) {
                cookieList.remove(tokenCookie);
                cookieList.add(getTokenCookie(url));
            }
        }
        return cookieList;
    }

    // 获取ResponseHeader中的cookie
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieMap.put(url.host(), cookies);
        if (url.toString().startsWith(mServerHost)) {
            for (Cookie cookie : cookies) {
                if (TOKEN_KEY.equalsIgnoreCase(cookie.name())) {
                    // TODO 本地持久化存储token
                    token = cookie.value();
                    break;
                }
            }
        }
    }

    //  TODO 将token设置在cookie中
    private Cookie getTokenCookie(HttpUrl url) {
        String key = url.host() + token;
        Cookie cookie = tokenCookieMap.get(key);
        if (null == cookie) {
            Cookie.Builder builder = new Cookie.Builder();
            builder.domain(url.host());  // cookie的作用域
            builder.name(TOKEN_KEY);
            builder.value(token);
            cookie = builder.build();
            tokenCookieMap.put(key, cookie);
        }
        return cookie;
    }

    public static void clearCookies() {
        cookieMap.clear();
    }
}
