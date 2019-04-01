package com.smq.commerce.bean;

/**
 * Time:2019/3/28
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ShopSelectListBean {
    private int commodityId;
    private int count;

    public ShopSelectListBean(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
