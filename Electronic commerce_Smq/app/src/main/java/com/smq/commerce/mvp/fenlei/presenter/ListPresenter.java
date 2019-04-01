package com.smq.commerce.mvp.fenlei.presenter;

import com.smq.commerce.bean.OneListinfo;
import com.smq.commerce.bean.ThreeListinfo;
import com.smq.commerce.bean.TwoListinfo;
import com.smq.commerce.content.ListContach;
import com.smq.commerce.mvp.fenlei.model.ListModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Time:2019/4/1
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ListPresenter implements ListContach.ListContachPresenter<ListContach.ListContachView> {
    //软应用
    private Reference<ListContach.ListContachView> reference;
    private ListContach.ListContachView listContachView;
    private ListModel listModel;


    @Override
    public void attachView(ListContach.ListContachView listContachView) {
        //绑定V层
        this.listContachView = listContachView;
        //绑定
        reference = new WeakReference<>(listContachView);
        //实例M层
        listModel = new ListModel();
    }

    @Override
    public void delachView(ListContach.ListContachView listContachView) {
        //解绑
        reference.clear();
    }

    @Override
    public void getOnelist(String url, CompositeDisposable disposable) {
        listModel.getOnelist(url, disposable, new ListContach.OnCallBack() {
            @Override
            public void getOnelist(List<OneListinfo.ResultBean> oneList) {
                listContachView.getOnelist(oneList);
            }

            @Override
            public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {

            }

            @Override
            public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {

            }
        });
    }

    @Override
    public void getTwoList(String url, String firstCategoryId, CompositeDisposable disposable) {
        listModel.getTwoList(url, firstCategoryId, disposable, new ListContach.OnCallBack() {
            @Override
            public void getOnelist(List<OneListinfo.ResultBean> oneList) {

            }

            @Override
            public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {
                listContachView.getTwoList(tworesult);
            }

            @Override
            public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {

            }
        });
    }

    @Override
    public void getThreeList(String url, String firstCategoryId, int page, int count, CompositeDisposable disposable) {
        listModel.getThreeList(url, firstCategoryId, page, count, disposable, new ListContach.OnCallBack() {
            @Override
            public void getOnelist(List<OneListinfo.ResultBean> oneList) {

            }

            @Override
            public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {

            }

            @Override
            public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {
                listContachView.getThreeList(threeresult);
            }
        });
    }

}
