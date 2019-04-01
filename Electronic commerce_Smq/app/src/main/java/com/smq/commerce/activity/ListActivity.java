package com.smq.commerce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.smq.commerce.R;
import com.smq.commerce.adapter.fenlei.ListOneAdapter;
import com.smq.commerce.adapter.fenlei.ListThreeAdapter;
import com.smq.commerce.adapter.fenlei.ListTwoAdapter;
import com.smq.commerce.api.Api;
import com.smq.commerce.base.BaseActivity3;
import com.smq.commerce.bean.OneListinfo;
import com.smq.commerce.bean.ThreeListinfo;
import com.smq.commerce.bean.TwoListinfo;
import com.smq.commerce.content.ListContach;
import com.smq.commerce.mvp.fenlei.presenter.ListPresenter;
import com.smq.commerce.widget.Custom_home;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Time:2019/4/1
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class ListActivity extends BaseActivity3 implements View.OnClickListener, ListContach.ListContachView {
    private Custom_home custom_home;
    private RecyclerView recycle;
    private ListPresenter listPresenter;
    private ImageView common;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerView towre;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        custom_home = findViewById(R.id.li_search);
        recycle = findViewById(R.id.li_recycle);
        common = findViewById(R.id.custom_home_classification);
        //设置样式
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recycle.setLayoutManager(manager);
        common.setOnClickListener(this);

        listPresenter = new ListPresenter();
        listPresenter.attachView(this);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String mid = intent.getStringExtra("Mid");
        Log.i("three-------", mid);
        listPresenter.getThreeList(Api.SeatchUrl, mid, 1, 6, disposable);

        //回调放法
        custom_home.setOnIntent(new Custom_home.OnIntent() {
            @Override
            public void onintent() {
                //跳转到搜索页面
                Intent intent = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_home_classification:
                //请求一级列表数据
                listPresenter.getOnelist(Api.SeatchUrl, disposable);
                break;
        }
    }

    //one
    @Override
    public void getOnelist(List<OneListinfo.ResultBean> oneList) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_onelist, null, false);
        //RecyclerView列表控件
        RecyclerView ontList = view.findViewById(R.id.one_list);
        towre = view.findViewById(R.id.two_list);
        //设置管理器
        ontList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListOneAdapter listOneAdapter = new ListOneAdapter(this, oneList);
        ontList.setAdapter(listOneAdapter);

        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        //这是背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setTouchable(true);
        //显示
        popupWindow.showAsDropDown(custom_home);

        listOneAdapter.setOnOneClick(new ListOneAdapter.OnOneClick() {
            @Override
            public void setIdData(String id) {
                //请求二级列表
                listPresenter.getTwoList(Api.SeatchUrl, id, disposable);
            }
        });
    }

    //two
    @Override
    public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {
        //设置管理器
        towre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListTwoAdapter listTwoAdapter = new ListTwoAdapter(this, tworesult);
        towre.setAdapter(listTwoAdapter);
        listTwoAdapter.setOnTwoClick(new ListTwoAdapter.OnTwoClick() {
            @Override
            public void setIdData(String id) {
                Log.i("three-------", id);
                listPresenter.getThreeList(Api.SeatchUrl, id, 1, 6, disposable);
            }
        });
    }

    //three
    @Override
    public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {
        ListThreeAdapter listThreeAdapter = new ListThreeAdapter(this, threeresult);
        recycle.setAdapter(listThreeAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        listPresenter.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //消除订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }
}
