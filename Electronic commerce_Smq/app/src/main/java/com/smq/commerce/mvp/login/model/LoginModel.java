package com.smq.commerce.mvp.login.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.LoginBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class LoginModel implements ILoginModel{


    @Override
    public void login( Map<String, String> map, final ILoginCallBack iLoginCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.Login_Url, ApiService.class);
        Observable<LoginBean> loginBeanObservable = apiService.LoginShow(map);
        loginBeanObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        iLoginCallBack.onStatus(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
