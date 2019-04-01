package com.smq.commerce.mvp.car.model;

import java.util.HashMap;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface IShopCartModel {
    void getData(int uid,String sid, ModelCallBack modelCallBack);
    void queryCart(int uid, String sid, HashMap<String,String> pramrs, ModelCallBack modelCallBack);
    interface ModelCallBack{
        void loadSuccess(Object o);
        void loadFailed(Throwable throwable);
    }

}
