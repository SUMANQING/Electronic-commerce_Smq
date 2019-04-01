package com.smq.commerce.mvp.findadd.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.AddressList;
import com.smq.commerce.utils.RetrofitUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Time:2019/3/25
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class FindAddressModel implements IFindAddressModel {
    @Override
    public void getFindAddrData(String userId, String sessionId, final FindModelInterface findModelInterface) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.ADDRESS, ApiService.class);
        apiService.findAddress(userId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddressList value) {
                        findModelInterface.findSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        findModelInterface.findFaile(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
