package com.smq.commerce.mvp.search.view;

import com.smq.commerce.bean.SearchBean;

import java.util.List;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public interface SearchView {
    void getHttpData(List<SearchBean.ResultBean> result);
}
