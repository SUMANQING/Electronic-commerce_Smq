package com.smq.commerce.mvp.insertadd.presenter;

import com.smq.commerce.activity.AddressActivity;
import com.smq.commerce.mvp.insertadd.model.AddrModel;
import com.smq.commerce.mvp.insertadd.model.IAddrModel;
import com.smq.commerce.mvp.insertadd.presenter.IAddadressPresenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Time:2019/3/23
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class AddadressPresenter<T> implements IAddadressPresenter {
    AddressActivity addAdressActivity;
    private final AddrModel addrModel;
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
    public AddadressPresenter(AddressActivity addAdressActivity) {
        this.addAdressActivity = addAdressActivity;
        addrModel = new AddrModel();
    }

    public void getAddPresenter(String userId, String sessionId, String real, String phone, String xiang, String zipcode) {
        addrModel.getAddData(userId, sessionId, real, phone, xiang, zipcode, new IAddrModel.AddModelInterface() {
            @Override
            public void loadAddSuccess(Object o) {
                addAdressActivity.getAddrViewData(o);
            }

            @Override
            public void loadAddFailed(Throwable throwable) {

            }
        });
    }
}
