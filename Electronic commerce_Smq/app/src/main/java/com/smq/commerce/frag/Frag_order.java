package com.smq.commerce.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smq.commerce.R;
import com.smq.commerce.adapter.order.FmPagerAdapter;
import com.smq.commerce.frag.Order.AllFragment;
import com.smq.commerce.frag.Order.FaFragment;
import com.smq.commerce.frag.Order.FuFragment;
import com.smq.commerce.frag.Order.PingFragment;
import com.smq.commerce.frag.Order.ShouFragment;

import java.util.ArrayList;

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
public class Frag_order extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"全部", "待付款", "代发货","待收货","待评价"};
    private int[] pic=new int[]{R.mipmap.common_icon_all_list_n_hdpi,R.mipmap.common_icon_pay_n_hdpi,R.mipmap.common_icon_receive_n,R.mipmap.commom_icon_comment_n_hdpi,R.mipmap.common_icon_finish_n_hdpi,};
    private FmPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order, null, false);
        unbinder = ButterKnife.bind(this, view);
        
        initView();
        
        return view;
    }

    private void initView() {
        fragments.add(new AllFragment());
        fragments.add(new FaFragment());
        fragments.add(new FuFragment());
        fragments.add(new ShouFragment());
        fragments.add(new PingFragment());
        for(int i=0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewpager,false);
        pagerAdapter = new FmPagerAdapter(fragments,getActivity().getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for(int i=0;i<titles.length;i++){
            tabLayout.getTabAt(i).setText(titles[i]).setIcon(pic[i]);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
