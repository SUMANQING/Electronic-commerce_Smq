package com.smq.commerce.api;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Api {

    public static final String SYNCSHOPPINGCART="http://mobile.bwstudent.com//small/order/verify/v1/syncShoppingCart";

    public static final String BASE_URL = "http://mobile.bwstudent.com/";
    //商品首页
    //http://172.17.8.100/small/commodity/v1/commodityList
    public static final String Home_Url="http://172.17.8.100/small/commodity/v1/";

    //搜索
    //http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?keyword=%E9%AB%98%E8%B7%9F%E9%9E%8B&page=1&count=5
    public static final String SeatchUrl="http://172.17.8.100/";

    //登录
    //http://172.17.8.100/small/user/v1/login
    public static final String Login_Url="http://172.17.8.100/small/user/v1/";

    //注册
    //http://172.17.8.100/small/user/v1/register
    public static final String Regist_Url="http://172.17.8.100/small/user/v1/";

    // 添加收货地址
    //http://172.17.8.100/small/user/verify/v1/addReceiveAddress
    public static final String INSERT_ADDRESS = "http://172.17.8.100/small/user/verify/v1/";

    //收货地址列表
    public static final String ADDRESS = "http://172.17.8.100/small/user/verify/v1/";
}
