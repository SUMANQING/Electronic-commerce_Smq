package com.smq.commerce.mvp.create.model;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.CreationShopBean;
import com.smq.commerce.bean.DefaultBean;
import com.smq.commerce.bean.SelectAddressBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class CreationModel implements ICreationModel {
    @Override
    public void getData(HashMap<String, String> pramrs, HashMap<String, String> maps, final ModelCallBack modelCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.CreatDing(maps,pramrs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CreationShopBean>() {
                    @Override
                    public void accept(CreationShopBean creationShopBean) throws Exception {
                        modelCallBack.loadSuccess(creationShopBean);
                    }
                });
    }

    @Override
    public void gteDiZhiData(HashMap<String, String> pramrs, HashMap<String, String> maps, final ModelCallBack modelCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.deFaultDi(pramrs,maps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DefaultBean>() {
                    @Override
                    public void accept(DefaultBean defaultBean) throws Exception {
                        modelCallBack.loadSuccess(defaultBean);
                    }
                });
    }

    @Override
    public void getShouHuo(HashMap<String, String> maps, final ModelCallBack modelCallBack) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.ShouHuo(maps)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SelectAddressBean>() {
                    @Override
                    public void accept(SelectAddressBean selectAddressBean) throws Exception {
                        modelCallBack.loadSuccess(selectAddressBean);
                    }
                });
    }
}
