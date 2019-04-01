package com.smq.commerce.bean;

import java.util.List;

/**
 * Time:2019/3/25
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class AddressList {

    /**
     * result : [{"address":"北京市 北京市 海淀区八维研修学院","createTime":1551478399000,"id":224,"phone":"18436003581","realName":"cookie","userId":174,"whetherDefault":1,"zipCode":"100000"},{"address":"北京市 北京市 朝阳区上地七街","createTime":1551478623000,"id":225,"phone":"18436003581","realName":"MLH","userId":174,"whetherDefault":2,"zipCode":"130000"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * address : 北京市 北京市 海淀区八维研修学院
         * createTime : 1551478399000
         * id : 224
         * phone : 18436003581
         * realName : cookie
         * userId : 174
         * whetherDefault : 1
         * zipCode : 100000
         */

        private String address;
        private long createTime;
        private int id;
        private String phone;
        private String realName;
        private int userId;
        private int whetherDefault;
        private String zipCode;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherDefault() {
            return whetherDefault;
        }

        public void setWhetherDefault(int whetherDefault) {
            this.whetherDefault = whetherDefault;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }
    }
}
