package com.smq.commerce.mvp.regist.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.RegistBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class RegistModel implements IRegistModel{
    @Override
    public void regist(Map<String, String> map, final IRegistCallBack callBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.Regist_Url, ApiService.class);
        apiService.RegistShow(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegistBean registBean) {
                        callBack.onStatus(registBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
