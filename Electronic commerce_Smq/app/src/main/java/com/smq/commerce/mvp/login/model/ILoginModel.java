package com.smq.commerce.mvp.login.model;

import java.util.Map;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface ILoginModel {
    void login(Map<String,String> map, ILoginCallBack iLoginCallBack);
    //定义接口
    interface ILoginCallBack{
        void onStatus(Object o);
        void onFailed();
    }
}
