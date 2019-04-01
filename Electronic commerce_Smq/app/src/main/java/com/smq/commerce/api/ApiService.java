package com.smq.commerce.api;

import com.smq.commerce.bean.AddShoppBean;
import com.smq.commerce.bean.AddressList;
import com.smq.commerce.bean.CreationShopBean;
import com.smq.commerce.bean.DefaultBean;
import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.HomeBean;
import com.smq.commerce.bean.InsertAddress;
import com.smq.commerce.bean.LoginBean;
import com.smq.commerce.bean.OneListinfo;
import com.smq.commerce.bean.RegistBean;
import com.smq.commerce.bean.SearchBean;
import com.smq.commerce.bean.SelectAddressBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.bean.ThreeListinfo;
import com.smq.commerce.bean.TwoListinfo;
import com.smq.commerce.bean.addMoreBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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

    //详情数据
    @GET("small/commodity/v1/findCommodityDetailsById")
    Call<DetailsBean>getdetails(@Query("commodityId")int commodityId);

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

    /**
     * 收货地址列表
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("receiveAddressList")
    Observable<AddressList> findAddress(@Header("userId") String userId, @Header("sessionId") String sessionId);

    //查询购物车
    @GET("small/order/verify/v1/findShoppingCart")
    Call<ShoppBean> getshopp();

    //加入购物车
    @PUT("small/order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Call<AddShoppBean> shoppingcar(@Field("data") String s);

    //查询购物车2
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<ShoppBean> findCart(@Header("userId") int uid, @Header("sessionId") String sid);

    //购物车页面的添加
    @PUT("small/order/verify/v1/syncShoppingCart")
    Observable<AddShoppBean> tbCars(@Header("userId") int userId, @Header("sessionId") String sessionId, @QueryMap HashMap<String,String> map);

    //创建订单
    @POST("small/order/verify/v1/createOrder")
    Observable<CreationShopBean> CreatDing(@HeaderMap HashMap<String,String> maps, @QueryMap HashMap<String,String> map);

    //设置默认收货地址
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    Observable<DefaultBean> deFaultDi(@HeaderMap HashMap<String,String> maps, @QueryMap HashMap<String,String> pars);

    //查询收货地址
    @GET("small/user/verify/v1/receiveAddressList")
    Observable<SelectAddressBean> ShouHuo(@HeaderMap HashMap<String,String> maps);

    //OneList
    @GET("small/commodity/v1/findFirstCategory")
    Flowable<OneListinfo> getOneList();

    //OneList
    @GET("small/commodity/v1/findSecondCategory")
    Flowable<TwoListinfo> getTwoList(@Query("firstCategoryId") String firstCategoryId);

    //ThreeList
    @GET("small/commodity/v1/findCommodityByCategory")
    Flowable<ThreeListinfo> getThreeList(@Query("categoryId") String firstCategoryId, @Query("page") int page, @Query("count") int count);
}
