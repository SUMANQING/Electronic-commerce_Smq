package com.smq.commerce.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Time:2019/3/27
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public abstract class BasePresenter<T> {
    public abstract  void date(Map<String, String> map);
    public abstract  void regdate(Map<String, String> map);

    //结局内存泄漏
    private Reference<T> mviewModel;
    //与P层关联 处理内存泄露
    public  void  attachview (T view){
        mviewModel =new WeakReference<T>(view);
    }
    // P层解除关联
    public  void  detachview(){
        if (mviewModel.get()!=null){
            mviewModel.clear();
            mviewModel=null;
        }
    }
}
