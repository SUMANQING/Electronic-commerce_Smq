package com.smq.commerce.mvp.regist.model;

import java.util.Map;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface IRegistModel {
    void regist(Map<String,String> map, IRegistCallBack callBack);
    //定义接口
    interface IRegistCallBack{
        void onStatus(Object o);
        void onFailed(Throwable throwable);
    }
}
