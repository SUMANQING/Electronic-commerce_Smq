package com.smq.commerce.mvp.regist.presenter;

import com.smq.commerce.activity.RegistActivity;
import com.smq.commerce.mvp.regist.model.IRegistModel;
import com.smq.commerce.mvp.regist.model.RegistModel;
import com.smq.commerce.mvp.regist.presenter.IRegistPresenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class RegistPresenter<T> implements IRegistPresenter {
    RegistActivity registActivity;
    private final RegistModel registModel;
    private Reference<T> tReference;
    public void attachView(T t){
        tReference=new WeakReference<T>(t);
    }
    public void deatchView(){
        if (tReference!=null){
            tReference.clear();
            tReference=null;
        }
    }
    public RegistPresenter(RegistActivity registActivity) {
        this.registActivity = registActivity;
        registModel = new RegistModel();
    }

    public void registPre(Map<String,String> map) {
        registModel.regist(map, new IRegistModel.IRegistCallBack() {
            @Override
            public void onStatus(Object o) {
                registActivity.showMsg(o);
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }
}
