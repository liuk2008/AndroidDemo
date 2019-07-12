package com.android.demo.mvp.presenter;


import com.android.demo.mvp.entity.UserInfo;

import java.util.LinkedHashMap;

/**
 * 业务层回调方法
 * Created by Administrator on 2018/2/1.
 */
public interface BaseResult {

    interface LoginResult {
        void loginSuccess(UserInfo userInfo);

        void loginFail(int resultCode, String errorMsg);
    }

    interface CheckPhoneResult {

        void checkPhoneSuccess(LinkedHashMap<String, Object> linkedHashMap);

        void checkPhoneFail(int resultCode, String errorMsg);
    }

}
