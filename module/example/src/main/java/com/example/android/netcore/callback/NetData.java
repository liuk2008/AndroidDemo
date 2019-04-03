package com.example.android.netcore.callback;

import java.io.Serializable;

/**
 * 封装网络层数据
 * Created by Administrator on 2018/4/10.
 */

public class NetData implements Serializable {

    private static final long serialVersionUID = 835238407293265994L;
    private int code;
    private String msg;

    public NetData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageStatus{");
        sb.append("code=").append(code);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
