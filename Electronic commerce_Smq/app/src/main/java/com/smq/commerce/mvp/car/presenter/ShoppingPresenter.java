package com.smq.commerce.mvp.car.presenter;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.base.BasePresenter;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.frag.Frag_car;
import com.smq.commerce.mvp.car.model.IShopCartModel;
import com.smq.commerce.mvp.car.model.ShoppModel;
import com.smq.commerce.mvp.car.view.ShoppView;
import com.smq.commerce.utils.RetrofitUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ShoppingPresenter<T> implements  IShopCartPresenter{

    Frag_car shopCarFragment;
    private final ShoppModel shopCartModel;
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
    public ShoppingPresenter(Frag_car shopCarFragment) {
        this.shopCarFragment = shopCarFragment;
        shopCartModel = new ShoppModel();
    }


    @Override
    public void getPresenterData(int uid,String sid) {
        shopCartModel.getData(uid,sid,new IShopCartModel.ModelCallBack() {
            @Override
            public void loadSuccess(Object o) {
                shopCarFragment.getViewsData(o);
            }

            @Override
            public void loadFailed(Throwable throwable) {

            }
        });
    }

    @Override
    public void getQueryData(int uid, String sid, HashMap<String, String> maps) {
        shopCartModel.queryCart(uid, sid, maps, new IShopCartModel.ModelCallBack() {
            @Override
            public void loadSuccess(Object o) {

            }

            @Override
            public void loadFailed(Throwable throwable) {

            }
        });
    }

    public  void  deatch(){
        if(shopCarFragment!=null){
            shopCarFragment=null;
        }
    }
}
