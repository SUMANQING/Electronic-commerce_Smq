package com.smq.commerce.bean;

/**
 * Time:2019/3/27
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class addMoreBean {
    private String commodityId;
    private String count;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public addMoreBean(String commodityId, String count) {
        this.commodityId = commodityId;
        this.count = count;
    }
}
