package com.smq.commerce.bean;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class AddressListBean {
    private String City;
    private String address;

    public AddressListBean(String city, String address) {
        City = city;
        this.address = address;
    }

    public AddressListBean() {
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
