package com.android.demo.mvp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthBillInfo implements Serializable {
    private static final long serialVersionUID = -1;
    @SerializedName("invest_money")
    public BigDecimal invest_money;
    @SerializedName("total_people")
    public int total_people;
    @SerializedName("invest_cnt")
    public int invest_cnt;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonthBillInfo{");
        sb.append("invest_money=").append(invest_money);
        sb.append(", total_people=").append(total_people);
        sb.append(", invest_cnt=").append(invest_cnt);
        sb.append('}');
        return sb.toString();
    }
}
