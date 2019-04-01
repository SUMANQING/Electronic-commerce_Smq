package com.smq.commerce.mvp.create;

import java.util.HashMap;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface ICreationPresenter {
    void getPresenterData(HashMap<String, String> maps, HashMap<String, String> pramrs);
    void getDiZhiData(HashMap<String, String> maps,HashMap<String, String> pramrs);
    void getShouHuo(HashMap<String,String> maps);
}
