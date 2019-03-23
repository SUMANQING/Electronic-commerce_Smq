package com.smq.commerce.mvp.home.presenter;

import com.smq.commerce.bean.HomeBean;
import com.smq.commerce.mvp.home.model.HomeModel;
import com.smq.commerce.mvp.home.view.HomeView;

import java.util.List;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class HomePresenter {

    private final HomeModel homeModel;
    private final HomeView homeView;

    public HomePresenter(HomeView view) {
        homeModel = new HomeModel();
        homeView = view;
    }

    public void onRetlaed() {
        homeModel.getHttpData();
        homeModel.setOnHomeListener(new HomeModel.onHomeListener() {
            @Override
            public void onrequest(HomeBean.ResultBean result) {
                homeView.getHttpData(result);
            }
        });
    }
}
