package com.smq.commerce.mvp.insertadd.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.InsertAddress;
import com.smq.commerce.utils.RetrofitUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Time:2019/3/23
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class AddrModel implements IAddrModel{

    @Override
    public void getAddData(String userId, String sessionId, String realName, String phone, String address, String zipCode, final AddModelInterface addModelInterface) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.INSERT_ADDRESS, ApiService.class);
        apiService.insertAddress(userId,sessionId,realName, phone, address, zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InsertAddress>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InsertAddress value) {
                        addModelInterface.loadAddSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        addModelInterface.loadAddFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
