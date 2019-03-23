package com.smq.commerce.api;

import com.smq.commerce.bean.HomeBean;
import com.smq.commerce.bean.InsertAddress;
import com.smq.commerce.bean.LoginBean;
import com.smq.commerce.bean.RegistBean;
import com.smq.commerce.bean.SearchBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface ApiService {
    //商品首页
    @GET("commodityList")
    Call<HomeBean> HomeShow();

    //搜素
    @GET("small/commodity/v1/findCommodityByKeyword")
    Call<SearchBean> SearchShow(@Query("keyword")String keyword, @Query("page")int page, @Query("count")int count);

    //登录
    @POST("login")
    @FormUrlEncoded
    Observable<LoginBean> LoginShow(@FieldMap Map<String, String> map);

    //注册
    @POST("register")
    @FormUrlEncoded
    Observable<RegistBean> RegistShow(@FieldMap Map<String,String> map);

    @POST("addReceiveAddress")
    @FormUrlEncoded
    Observable<InsertAddress> insertAddress(@Header("userId") String userId, @Header("sessionId") String sessionId, @Field("realName") String realName, @Field("phone") String phone, @Field("address") String address, @Field("zipCode") String zipCode);
}
