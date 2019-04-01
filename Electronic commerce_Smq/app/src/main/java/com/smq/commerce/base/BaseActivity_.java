package com.smq.commerce.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.smq.commerce.utils.NetWorkUtils;

/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public abstract class BaseActivity_  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        NetWorkUtils.getConnectivityManager(getActivitys());
        initView(savedInstanceState);
        initData();
        initTitle();
        initListener();


    }

    public abstract void initView(Bundle savedInstanceState);

    public abstract Context getActivitys();

    public abstract void initListener();

    public abstract void initTitle();

    public abstract int initLayout();

    public abstract void initData();
}
