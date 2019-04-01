package com.smq.commerce.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Time:2019/4/1
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public abstract class BaseActivity3  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //得到当前界面的布局文件id
        setContentView(getLayoutId());
        //获取控件
        initView();
        //加载数据
        initData();
    }

    //得到当前界面的布局文件id
    protected abstract int getLayoutId();

    //获取控件
    protected abstract void initView();

    //加载数据
    protected abstract void initData();
}
