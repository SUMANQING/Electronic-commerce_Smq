package com.smq.commerce.mvp.findadd.model;

/**
 * Time:2019/3/25
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface IFindAddressModel {
    void getFindAddrData(String userId, String sessionId, FindModelInterface findModelInterface);
    interface FindModelInterface{
        void findSuccess(Object o);
        void findFaile(Throwable throwable);
    }
}
