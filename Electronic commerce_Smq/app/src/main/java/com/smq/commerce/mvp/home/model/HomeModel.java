package com.smq.commerce.mvp.home.model;

import android.util.Log;

import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.HomeBean;
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
public class HomeModel {
    public void getHttpData() {
        ApiService apiService = RetrofitUtils.getInstance().getApiService(Api.Home_Url, ApiService.class);
        apiService.HomeShow().enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                HomeBean body = response.body();
                HomeBean.ResultBean result = body.getResult();
                //Log.i("xxx",result.toString());
                if (onHomeListener!=null){
                    onHomeListener.onrequest( result);
                }
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                Log.i("xxx","失败");
            }
        });
    }

    public interface onHomeListener{
        void onrequest(HomeBean.ResultBean result);
    }
    private onHomeListener onHomeListener;

    public void setOnHomeListener(HomeModel.onHomeListener onHomeListener) {
        this.onHomeListener = onHomeListener;
    }
}
