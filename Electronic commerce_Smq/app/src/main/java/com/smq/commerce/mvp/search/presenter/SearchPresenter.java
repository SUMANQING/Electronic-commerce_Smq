package com.smq.commerce.mvp.search.presenter;

import com.smq.commerce.bean.SearchBean;
import com.smq.commerce.mvp.search.model.SearchModel;
import com.smq.commerce.mvp.search.view.SearchView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class SearchPresenter<T> {

    private final SearchModel searchModel;
    private final SearchView searchView;
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
    public SearchPresenter(SearchView view) {
        searchModel = new SearchModel();
        searchView = view;
    }

    public void onRetlade(String title, int page, int count) {
        searchModel.getHttpData(title,page,count);
        searchModel.setOnSearchListener(new SearchModel.onSearchListener() {
            @Override
            public void onRequest(List<SearchBean.ResultBean> result) {
                searchView.getHttpData(result);
            }
        });
    }
}
