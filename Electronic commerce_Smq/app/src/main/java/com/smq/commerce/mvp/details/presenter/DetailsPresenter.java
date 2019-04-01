package com.smq.commerce.mvp.details.presenter;

import com.smq.commerce.base.BasePresenter;
import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.mvp.details.model.DetailsModel;
import com.smq.commerce.mvp.details.view.DetailsView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Time:2019/3/27
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class DetailsPresenter<T> extends BasePresenter<DetailsView> {

    private final DetailsModel detailsModel;
    private final DetailsView detailsView;
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
    public DetailsPresenter(DetailsView view) {
        detailsModel = new DetailsModel();
        detailsView = view;
    }

    public void detailsDate(int id) {
        detailsModel.details(id);
        detailsModel.setHdate(new DetailsModel.Hdate() {
            @Override
            public void getdate(DetailsBean.ResultBean result) {
                detailsView.view(result);
            }

            @Override
            public void chashopp(List<ShoppBean.ResultBean> result) {
                detailsView.vieww(result);
            }

            @Override
            public void addshopp(String s) {
                detailsView.viewww(s);
            }
        });
    }

    //进行查询购物车
    public void chaShopp(String uid, String sid) {
        detailsModel.chaShopp(uid,sid);
    }

    //添加购物车
    public void addShopp(String uid, String sid, String sss) {
        detailsModel.addShopp(uid,sid,sss);
    }


    @Override
    public void date(Map<String, String> map) {

    }

    @Override
    public void regdate(Map<String, String> map) {

    }
}
