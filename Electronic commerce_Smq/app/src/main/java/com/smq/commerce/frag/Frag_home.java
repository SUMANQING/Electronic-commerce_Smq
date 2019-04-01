package com.smq.commerce.frag;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.recker.flybanner.FlyBanner;
import com.smq.commerce.R;
import com.smq.commerce.activity.ListActivity;
import com.smq.commerce.activity.SearchActivity;
import com.smq.commerce.adapter.fenlei.ListOneAdapter;
import com.smq.commerce.adapter.fenlei.ListTwoAdapter;
import com.smq.commerce.adapter.home.MyAdapter;
import com.smq.commerce.api.Api;
import com.smq.commerce.bean.HomeBean;
import com.smq.commerce.bean.OneListinfo;
import com.smq.commerce.bean.ThreeListinfo;
import com.smq.commerce.bean.TwoListinfo;
import com.smq.commerce.content.ListContach;
import com.smq.commerce.mvp.fenlei.presenter.ListPresenter;
import com.smq.commerce.mvp.home.presenter.HomePresenter;
import com.smq.commerce.mvp.home.view.HomeView;
import com.smq.commerce.widget.Custom_home;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Frag_home extends Fragment implements HomeView,ListContach.ListContachView  {
    @BindView(R.id.custom_home)
    Custom_home customHome;
    @BindView(R.id.home_fly)
    FlyBanner homeFly;
    @BindView(R.id.home_ryc)
    RecyclerView homeRyc;
    Unbinder unbinder;
    private ImageView search;
    private ImageView classification;
    private ListPresenter listPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerView towre;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null, false);
        unbinder = ButterKnife.bind(this, view);
        classification = view.findViewById(R.id.custom_home_classification);
        search = view.findViewById(R.id.custom_home_s);
        List<String> list=new ArrayList<>();
        list.add("http://172.17.8.100/images/small/banner/cj.png");
        list.add("http://172.17.8.100/images/small/banner/hzp.png");
        list.add("http://172.17.8.100/images/small/banner/lyq.png");
        list.add("http://172.17.8.100/images/small/banner/px.png");
        list.add("http://172.17.8.100/images/small/banner/wy.png");
        for (int i=0;i<list.size();i++){
            list.get(i);
        }
        homeFly.setImagesUrl(list);


        listPresenter = new ListPresenter();
        listPresenter.attachView((ListContach.ListContachView) this);

        HomePresenter homePresenter=new HomePresenter(this);
        homePresenter.onRetlaed();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        homeRyc.setLayoutManager(linearLayoutManager);

        getSearch();
        return view;
    }

    private void getSearch() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "hh", Toast.LENGTH_SHORT).show();
                listPresenter.getOnelist(Api.SeatchUrl,disposable);
            }
        });
    }

    @Override
    public void getHttpData(HomeBean.ResultBean result) {
        if (result!=null){
            MyAdapter adapter=new MyAdapter(getActivity(),result);
            homeRyc.setAdapter(adapter);
        }

    }


    @Override
    public void getOnelist(List<OneListinfo.ResultBean> oneList) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_onelist, null, false);
        //RecyclerView列表控件
        RecyclerView ontList = view.findViewById(R.id.one_list);
        towre = view.findViewById(R.id.two_list);
        //设置管理器
        ontList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListOneAdapter listOneAdapter = new ListOneAdapter(getActivity(), oneList);
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
        popupWindow.showAsDropDown(customHome);

        listOneAdapter.setOnOneClick(new ListOneAdapter.OnOneClick() {
            @Override
            public void setIdData(String id) {
                //请求二级列表
                listPresenter.getTwoList(Api.SeatchUrl, id, disposable);
            }
        });
    }

    @Override
    public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {
//设置管理器
        towre.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListTwoAdapter listTwoAdapter = new ListTwoAdapter(getActivity(), tworesult);
        towre.setAdapter(listTwoAdapter);
        listTwoAdapter.setOnTwoClick(new ListTwoAdapter.OnTwoClick() {
            @Override
            public void setIdData(String id) {
                //跳转到显示页面
                Intent intent = new Intent(getActivity(), ListActivity.class);
                //传值
                intent.putExtra("Mid", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
