package com.smq.commerce.mvp.details.model;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.smq.commerce.api.Api;
import com.smq.commerce.api.ApiService;
import com.smq.commerce.bean.AddShoppBean;
import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.utils.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Time:2019/3/27
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class DetailsModel {
    public void details(int id) {
        ApiService apiService = RetrofitUtils.getInstance().getApiServiceeeee(Api.SeatchUrl, ApiService.class);
        Call<DetailsBean> getdetails = apiService.getdetails(id);
        getdetails.enqueue(new Callback<DetailsBean>() {
            @Override
            public void onResponse(Call<DetailsBean> call, Response<DetailsBean> response) {
                DetailsBean.ResultBean result = response.body().getResult();

                    hdate.getdate(result);
               // Log.d("zzzzz",result.toString());
            }

            @Override
            public void onFailure(Call<DetailsBean> call, Throwable t) {
              //  Log.i("xxx","失败");
            }
        });
    }

    //先查询购物车的操作
    public void chaShopp(String uid, String sid) {
        //找网络工具类
        ApiService apiService = RetrofitUtils.getInstance().getserviserHand(Api.SeatchUrl,uid,sid,ApiService.class);
        Call<ShoppBean> call = apiService.getshopp();
        call.enqueue(new Callback<ShoppBean>() {
            @Override
            public void onResponse(Call<ShoppBean> call, Response<ShoppBean> response) {
                List<ShoppBean.ResultBean> result = response.body().getResult();
                /*  Log.i("xxx",result+"");*/
                hdate.chashopp(result);
            }

            @Override
            public void onFailure(Call<ShoppBean> call, Throwable t) {

            }
        });
    }


    public void addShopp(String uid, String sid, String sss) {
        ApiService apiService = RetrofitUtils.getInstance().getInstance().getserviserHand(Api.SeatchUrl, uid, sid, ApiService.class);
        Call<AddShoppBean> shoppingcar = apiService.shoppingcar(sss);
        shoppingcar.enqueue(new Callback<AddShoppBean>() {
            @Override
            public void onResponse(Call<AddShoppBean> call, Response<AddShoppBean> response) {
                AddShoppBean body = response.body();
                String message = body.getMessage();
                hdate.addshopp(message);
            }

            @Override
            public void onFailure(Call<AddShoppBean> call, Throwable t) {

            }
        });
    }

    //接口回调
    public interface Hdate{
        void getdate(DetailsBean.ResultBean result);
        void chashopp(List<ShoppBean.ResultBean> result);
        void addshopp(String s);
    }
    private Hdate hdate;

    public void setHdate(Hdate hdate) {
        this.hdate = hdate;
    }
}
