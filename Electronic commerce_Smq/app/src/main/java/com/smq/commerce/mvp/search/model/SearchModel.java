package com.smq.commerce.mvp.search.model;

import android.util.Log;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.SearchBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class SearchModel {

    public void getHttpData(String title, int page, int count) {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.SeatchUrl, ApiService.class);
        apiService.SearchShow(title,page,count).enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean body = response.body();
                List<SearchBean.ResultBean> result = body.getResult();
               // Log.i("xxx",result.toString());
                if (onSearchListener!=null){
                    onSearchListener.onRequest(result);
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                Log.i("xxx","失败");
            }
        });
    }

    public interface onSearchListener{
        void onRequest(List<SearchBean.ResultBean> result);
    }
    private onSearchListener onSearchListener;

    public void setOnSearchListener(SearchModel.onSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }
}
