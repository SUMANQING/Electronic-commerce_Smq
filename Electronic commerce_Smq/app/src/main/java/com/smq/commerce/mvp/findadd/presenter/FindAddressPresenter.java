package com.smq.commerce.mvp.findadd.presenter;

import com.smq.commerce.activity.AddActivity;
import com.smq.commerce.mvp.findadd.model.FindAddressModel;
import com.smq.commerce.mvp.findadd.model.IFindAddressModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Time:2019/3/25
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class FindAddressPresenter<T> implements IFindAddressPresenter {
    AddActivity addressActivity;
    private final FindAddressModel findAddressModel;
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
    public FindAddressPresenter(AddActivity addressActivity) {
        this.addressActivity = addressActivity;
        findAddressModel = new FindAddressModel();
    }

    @Override
    public void getFindPresenter(String userId, String sessionId) {
        findAddressModel.getFindAddrData(userId, sessionId, new IFindAddressModel.FindModelInterface() {
            @Override
            public void findSuccess(Object o) {
                addressActivity.getFindView(o);
            }

            @Override
            public void findFaile(Throwable throwable) {

            }
        });
    }
}
