package com.smq.commerce.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.recker.flybanner.FlyBanner;
import com.smq.commerce.R;
import com.smq.commerce.activity.SearchActivity;
import com.smq.commerce.adapter.home.MyAdapter;
import com.smq.commerce.bean.HomeBean;
import com.smq.commerce.mvp.home.presenter.HomePresenter;
import com.smq.commerce.mvp.home.view.HomeView;
import com.smq.commerce.widget.Custom_home;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Time:2019/3/21
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class Frag_home extends Fragment implements HomeView {
    @BindView(R.id.custom_home)
    Custom_home customHome;
    @BindView(R.id.home_fly)
    FlyBanner homeFly;
    @BindView(R.id.home_ryc)
    RecyclerView homeRyc;
    Unbinder unbinder;
    private ImageView search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null, false);
        unbinder = ButterKnife.bind(this, view);
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
    }

    @Override
    public void getHttpData(HomeBean.ResultBean result) {
        if (result!=null){
            MyAdapter adapter=new MyAdapter(getActivity(),result);
            homeRyc.setAdapter(adapter);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
