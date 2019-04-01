package com.smq.commerce.mvp.create.model;

import java.util.HashMap;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface ICreationModel {
    void getData(HashMap<String, String> pramrs, HashMap<String, String> maps, ModelCallBack modelCallBack);
    void gteDiZhiData(HashMap<String, String> pramrs,HashMap<String, String> maps, ModelCallBack modelCallBack);
    void getShouHuo(HashMap<String,String> maps,ModelCallBack modelCallBack);
    interface ModelCallBack{
        void loadSuccess(Object o);
        void loadFailed(Throwable throwable);
    }
}
