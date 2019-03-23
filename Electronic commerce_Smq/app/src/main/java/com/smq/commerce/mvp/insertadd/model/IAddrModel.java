package com.smq.commerce.mvp.insertadd.model;

/**
 * Time:2019/3/23
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface IAddrModel {
    void getAddData(String userId, String sessionId, String realName, String phone, String address, String zipCode, AddModelInterface addModelInterface);
    interface AddModelInterface{
        void loadAddSuccess(Object o);
        void loadAddFailed(Throwable throwable);
    }
}
