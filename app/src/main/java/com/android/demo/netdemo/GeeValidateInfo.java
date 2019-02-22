package com.android.demo.netdemo;

import java.io.Serializable;

/**
 * Created by shmyh on 2018/3/19.
 */

public class GeeValidateInfo implements Serializable {
    private static final long serialVersionUID = -1;

    public String geetest_challenge;

    public String geetest_validate;

    public String geetest_seccode;

    public int gtServerStatus;

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

    public int getGtServerStatus() {
        return gtServerStatus;
    }

    public void setGtServerStatus(int gtServerStatus) {
        this.gtServerStatus = gtServerStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeeValidateInfo{");
        sb.append("geetest_challenge='").append(geetest_challenge).append('\'');
        sb.append(", geetest_validate='").append(geetest_validate).append('\'');
        sb.append(", geetest_seccode='").append(geetest_seccode).append('\'');
        sb.append(", gtServerStatus=").append(gtServerStatus);
        sb.append('}');
        return sb.toString();
    }
}
