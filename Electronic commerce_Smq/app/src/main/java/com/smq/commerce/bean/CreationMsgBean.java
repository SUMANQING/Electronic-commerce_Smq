package com.smq.commerce.bean;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class CreationMsgBean {
    private String send;
    private Object flag;

    public CreationMsgBean(String send, Object flag) {
        this.send = send;
        this.flag = flag;
    }

    public CreationMsgBean() {
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }
}
