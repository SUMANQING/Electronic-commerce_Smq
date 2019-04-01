package com.smq.commerce.mvp.details.view;

import com.smq.commerce.bean.DetailsBean;
import com.smq.commerce.bean.ShoppBean;

import java.util.List;

/**
 * Time:2019/3/27
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface DetailsView {
    void view(DetailsBean.ResultBean result);
    void vieww(List<ShoppBean.ResultBean> result);
    void viewww(String message);
}
