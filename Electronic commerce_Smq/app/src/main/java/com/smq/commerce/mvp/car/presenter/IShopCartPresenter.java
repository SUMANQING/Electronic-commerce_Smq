package com.smq.commerce.mvp.car.presenter;

import java.util.HashMap;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface IShopCartPresenter {
    void getPresenterData(int uid,String sid);
    void getQueryData(int uid,String sid,HashMap<String,String> maps);
}
