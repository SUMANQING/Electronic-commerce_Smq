package com.smq.commerce.mvp.car.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.AddShoppBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ShoppModel implements IShopCartModel{

    @Override
    public void getData(int uid, String sid, final ModelCallBack modelCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.findCart(uid,sid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShoppBean>() {
                    @Override
                    public void accept(ShoppBean shopCart) throws Exception {
                        modelCallBack.loadSuccess(shopCart);
                    }
                });
    }

    @Override
    public void queryCart(int uid, String sid, HashMap<String, String> pramrs, ModelCallBack modelCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.tbCars(uid,sid,pramrs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AddShoppBean>() {
                    @Override
                    public void accept(AddShoppBean tongBuCart) throws Exception {

                    }
                });
    }
}
