package com.android.utils.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class NetUtils {

    /*
     * 判断网络是否连接
     */
    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();//  获取可用网络
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * 判断是否是wifi or 3g 是否连接
     * ConnectivityManager.TYPE_WIFI
     * ConnectivityManager.TYPE_MOBILE
     */
    public static boolean isNetType(Context context, int netType) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm)
            return false;
        return cm.getActiveNetworkInfo().getType() == netType;
    }

    /*
     * 获取网络连接类型
     */
    public static int getConnectedType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return info.getType();
                }
            }
        }
        return -1;
    }

}
