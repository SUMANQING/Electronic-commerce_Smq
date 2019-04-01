package com.smq.commerce.frag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.smq.commerce.R;
import com.smq.commerce.activity.CreateActivity;
import com.smq.commerce.activity.LoginActivity;
import com.smq.commerce.adapter.car.ShopAdapter;
import com.smq.commerce.base.BaseFragment;
import com.smq.commerce.bean.ShopSelectListBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.mvp.car.presenter.ShoppingPresenter;
import com.smq.commerce.mvp.car.view.ShoppView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
public class Frag_car extends BaseFragment {

    @BindView(R.id.shop_recy)
    RecyclerView shopRecy;
    @BindView(R.id.shop_box_all)
    CheckBox shopBoxAll;
    @BindView(R.id.shop_text_allprice)
    TextView shopTextAllprice;
    @BindView(R.id.shop_text_go)
    TextView shopTextGo;
    ShoppingPresenter mIpresenterImpl;

    private ShopAdapter selectShopAdapter;
    private List<ShoppBean.ResultBean> creation_bill;
    Unbinder unbinder;
    private int d;
    private String sessionId;

    @Override
    public int ResIdLayout() {
        return R.layout.frag_car;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
    }


    @Override
    public void initData() {
        //互绑
        initPresenter();
        //初始化RecyclerView
        initRecy();
        //结算总价格,数量
        getpriceCount();
        shopBoxAll.setChecked(false);
        //点击复选框进行判断
        onClickCheckAll();
        //点击跳转进行创建订单
        onClickCreation();

    }


    private void onClickCreation() {
        shopTextGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all_price = shopTextAllprice.getText().toString();
                if (!(all_price.equals("0")) && !(all_price.equals("0.0"))) {
                    Intent intent = new Intent(getActivity(), CreateActivity.class);
                    creation_bill = new ArrayList<>();
                    //判断商品是否被选中
                    //如果被选中就放到集合里，通过intent传到activity中
                    for (int i = 0; i < shop_list.size(); i++)
                        if (shop_list.get(i).isIscheck()) {
                            creation_bill.add(new ShoppBean.ResultBean(
                                    shop_list.get(i).getCommodityId(),
                                    shop_list.get(i).getCommodityName(),
                                    shop_list.get(i).getCount(),
                                    shop_list.get(i).getPic(),
                                    shop_list.get(i).getPrice()
                            ));
                        }
                    intent.putExtra("creation_bill", (Serializable) creation_bill);
                    startActivity(intent);
                }
            }
        });
    }


    private void onClickCheckAll() {
        shopBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断全选时商品的状态
                checkAll(shopBoxAll.isChecked());
                selectShopAdapter.notifyDataSetChanged();
            }
        });
    }

    List<ShoppBean.ResultBean> shop_list = new ArrayList<>();

    private void checkAll(boolean checked) {
        double totalPrice = 0;
        int num = 0;

        for (int i = 0; i < shop_list.size(); i++) {
            //遍历商品，改变状态
            shop_list.get(i).setIscheck(checked);
            totalPrice = totalPrice + (shop_list.get(i).getPrice() * shop_list.get(i).getCount());
            num = num + shop_list.get(i).getCount();
        }

        if (checked) {
            shopTextAllprice.setText("" + totalPrice);
            shopTextGo.setText("去结算(" + num + ")");
        } else {
            shopTextAllprice.setText("0");
            shopTextGo.setText("去结算");
        }
    }


    private void getpriceCount() {
        selectShopAdapter.setOnClick(new ShopAdapter.ShopClick() {
            @Override
            public void shopPrice(List<ShoppBean.ResultBean> list) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalNum = totalNum + list.get(i).getCount();
                    if (list.get(i).isIscheck()) {
                        totalPrice = totalPrice + list.get(i).getPrice() * list.get(i).getCount();
                        num = num + list.get(i).getCount();
                    }

                }
                if (num < totalNum) {
                    shopBoxAll.setChecked(false);
                } else {
                    shopBoxAll.setChecked(true);
                }
                shopTextAllprice.setText("" + totalPrice);
                shopTextGo.setText("去结算(" + num + ")");
                if (list.size() == 0) {
                    shopBoxAll.setChecked(false);
                }
            }
        });
        selectShopAdapter.setRemove(new ShopAdapter.RemoveCallBack() {
            @Override
            public void removeposition(List<ShoppBean.ResultBean> list, int position) {
                //在这里重新遍历已经改变状态后的数据
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalNum = totalNum + list.get(i).getCount();
                    if (list.get(i).isIscheck()) {
                        totalPrice = totalPrice + list.get(i).getPrice() * list.get(i).getCount();
                        num = num + list.get(i).getCount();
                    }

                }
                if (num < totalNum) {
                    shopBoxAll.setChecked(false);
                } else {
                    shopBoxAll.setChecked(true);
                }
                shop_list.remove(position);
                shopTextAllprice.setText("" + totalPrice);
                shopTextGo.setText("去结算(" + num + ")");
                //添加购物车的集合
                List<ShopSelectListBean> addlist = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    int commodityId = list.get(i).getCommodityId();
                    int count = list.get(i).getCount();
                    addlist.add(new ShopSelectListBean(Integer.valueOf(commodityId), count));
                }
                String data = "[";
                for (ShopSelectListBean bean : addlist) {
                    data += "{\"commodityId\":" + bean.getCommodityId() + ",\"count\":" + bean.getCount() + "},";
                }
                String substring = data.substring(0, data.length() - 1);
                substring += "]";
                HashMap<String, String> params = new HashMap<>();
                params.put("data", substring);
                mIpresenterImpl.getQueryData(d, sessionId, params);
                if (list.size() == 0) {
                    shopBoxAll.setChecked(false);
                }
            }
        });
    }


    private void initRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        shopRecy.setLayoutManager(linearLayoutManager);
        //设置适配器
        selectShopAdapter = new ShopAdapter(getActivity());
        shopRecy.setAdapter(selectShopAdapter);
    }

    private void initPresenter() {
        mIpresenterImpl = new ShoppingPresenter(this);
        SharedPreferences preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", "");
        sessionId = preferences.getString("sessionId", "");
        if (userId.equals("")){
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }else {
            d = Integer.parseInt(userId);
            mIpresenterImpl.getPresenterData(d,sessionId);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        mIpresenterImpl.deatch();
    }

    private long exitTime = 0;

    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

                    //双击退出
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
        mIpresenterImpl.getPresenterData(d,sessionId);
    }

    public void getViewsData(Object o) {
        if (o instanceof ShoppBean) {
            ShoppBean selectShopBean = (ShoppBean) o;
            // Log.i("asda", "getViewData: " + selectShopBean.getMessage());
            selectShopAdapter.setList(selectShopBean.getResult());
            shop_list = selectShopBean.getResult();
            shopBoxAll.setChecked(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
