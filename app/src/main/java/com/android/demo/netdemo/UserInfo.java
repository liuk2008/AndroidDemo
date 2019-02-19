package com.android.demo.netdemo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private static final long serialVersionUID = -1;
    @SerializedName("phone")
    public String phone;
    @SerializedName("userId")
    public String userId;
    @SerializedName("isNew")
    public boolean isNew;
    @SerializedName("rememberMe")
    public String rememberMe;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("phone='").append(phone).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", isNew=").append(isNew);
        sb.append(", rememberMe='").append(rememberMe).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
