package com.smq.commerce.mvp.create.presenter;

import com.smq.commerce.activity.CreateActivity;
import com.smq.commerce.mvp.create.ICreationPresenter;
import com.smq.commerce.mvp.create.model.CreationModel;
import com.smq.commerce.mvp.create.model.ICreationModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class CreationPresenter implements ICreationPresenter {
    CreateActivity shopCarFragment;
    private final CreationModel creationModel;

    public CreationPresenter(CreateActivity shopCarFragment) {
        this.shopCarFragment = shopCarFragment;
        creationModel = new CreationModel();
    }


    @Override
    public void getPresenterData(HashMap<String, String> maps, HashMap<String, String> pramrs) {
        creationModel.getData(maps,pramrs,new ICreationModel.ModelCallBack() {
            @Override
            public void loadSuccess(Object o) {
                shopCarFragment.getViewData(o);
            }

            @Override
            public void loadFailed(Throwable throwable) {

            }
        });
    }

    @Override
    public void getDiZhiData(HashMap<String, String> maps, HashMap<String, String> pramrs) {
        creationModel.gteDiZhiData(maps, pramrs, new ICreationModel.ModelCallBack() {
            @Override
            public void loadSuccess(Object o) {
                shopCarFragment.getDizhiData(o);
            }

            @Override
            public void loadFailed(Throwable throwable) {

            }
        });
    }

    @Override
    public void getShouHuo(HashMap<String, String> maps) {
        creationModel.getShouHuo(maps, new ICreationModel.ModelCallBack() {
            @Override
            public void loadSuccess(Object o) {
                shopCarFragment.getShopData(o);
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
