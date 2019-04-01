package com.smq.commerce.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smq.commerce.R;
import com.smq.commerce.adapter.add.MyAddressAdapter;
import com.smq.commerce.adapter.create.CreationRecyAdapter;
import com.smq.commerce.adapter.create.PopCreationAdapter;
import com.smq.commerce.base.BaseActivity_;
import com.smq.commerce.bean.AddressList;
import com.smq.commerce.bean.AddressListBean;
import com.smq.commerce.bean.CreationJsonBean;
import com.smq.commerce.bean.CreationMsgBean;
import com.smq.commerce.bean.CreationShopBean;
import com.smq.commerce.bean.DefaultBean;
import com.smq.commerce.bean.SelectAddressBean;
import com.smq.commerce.bean.ShoppBean;
import com.smq.commerce.mvp.create.presenter.CreationPresenter;
import com.smq.commerce.mvp.findadd.presenter.FindAddressPresenter;
import com.smq.commerce.mvp.findadd.view.IFindAddressView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivity extends BaseActivity_ {

    private TextView text_name;
    private TextView text_phone;
    private TextView text_address;
    private ImageView image_pop;
    private PopupWindow popupWindow;
    private RecyclerView pop_recy;
    private boolean flag=true;
    private RecyclerView creation_recy;
    private TextView text_allnum;
    private TextView text_allprice;
    private TextView text_go;
    private List<ShoppBean.ResultBean> list;
    private int addressId;
    private HashMap<String, String> maps;
    private CreationPresenter mIpresenterImpl;
    private CreationRecyAdapter creationRecyAdapter;
    private PopCreationAdapter popCreationAdapter;

    @Override
    public int initLayout() {
        return R.layout.activity_create;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        text_name = findViewById(R.id.creation_name);
        text_phone = findViewById(R.id.creation_phone);
        text_address = findViewById(R.id.creation_address);
        image_pop = findViewById(R.id.creation_image_pop);
        creation_recy = findViewById(R.id.creation_shop_recy);
        text_allnum = findViewById(R.id.cretion_text_allnum);
        text_allprice = findViewById(R.id.cretion_text_allprice);
        text_go = findViewById(R.id.creation_text_go);
    }

    @Override
    public void initData() {
        //互绑
        initIpresenter();
        //初始化recy]
        initRecy();
        //获取传递过来的值
        initIntent();
        //初始化initPoPupwindow
        initPoPupwindow();
        //初始化PoPupwindow的RecyclerView
        initPopRecy();
        //提交订单
        setCreation();
    }

    private void setCreation() {
        text_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交的字符串
                List<CreationJsonBean> addlist=new ArrayList<>();
                for ( ShoppBean.ResultBean bean : list){
                    CreationJsonBean creationShopBean=new CreationJsonBean();
                    creationShopBean.setCommodityId(bean.getCommodityId());
                    creationShopBean.setAmount(bean.getCount());
                    addlist.add(creationShopBean);
                }
                String orderInfo = new Gson().toJson(addlist);
                //支付的总金额
                String totalPrice = text_allprice.getText().toString();

                initCreationUrl(orderInfo,totalPrice,addressId);

                finish();
            }
        });
    }

    //创建订单的接口
    private void initCreationUrl(String orderInfo, String totalPrice, int addressId) {
        SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
        String usserId = preferences.getString("usserId", "");
        String seessionId = preferences.getString("seessionId", "");
        maps = new HashMap<>();
        maps.put("usserId",usserId);
        maps.put("seessionId",seessionId);
        HashMap<String,String> params=new HashMap<>();
        params.put("orderInfo",orderInfo);
        params.put("totalPrice",totalPrice);
        params.put("addressId",String.valueOf(addressId));
        mIpresenterImpl.getPresenterData(maps,params);
    }
    private void initRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        creation_recy.setLayoutManager(linearLayoutManager);
        creationRecyAdapter = new CreationRecyAdapter(this);
        creation_recy.setAdapter(creationRecyAdapter);
        creationRecyAdapter.setOnClick(new CreationRecyAdapter.ShopClick() {
            @Override
            public void shopPrice(List<ShoppBean.ResultBean> list) {
                //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
                double totalPrice=0;
                //商品购买的数量
                int num=0;
                for (int i=0;i<list.size();i++){
                    totalPrice=totalPrice+list.get(i).getPrice()*list.get(i).getCount();
                    num=num+list.get(i).getCount();
                }
                text_allnum.setText(""+num);
                text_allprice.setText(""+totalPrice);
            }
        });
    }

    private void initPopRecy() {
        //布局管理器
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(CreateActivity.this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        pop_recy.setLayoutManager(linearLayoutManager1);
        popCreationAdapter = new PopCreationAdapter(CreateActivity.this);
        pop_recy.setAdapter(popCreationAdapter);
        popCreationAdapter.setChange(new PopCreationAdapter.ClickChange() {
            @Override
            public void onClick(int id) {
                HashMap<String,String> params=new HashMap<>();
                params.put("id",String.valueOf(id));
                mIpresenterImpl.getDiZhiData(maps,params);
            }
        });
    }

    private void initPoPupwindow() {
        // 用于PopupWindow的View
        View contentView=LayoutInflater.from(this).inflate(R.layout.creation_bill_pop, null, false);

        pop_recy = contentView.findViewById(R.id.pop_creation_recy);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
       /* // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);*/
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.orange));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        //点击弹出
        image_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.showAsDropDown(v,0,26);
                // image_pop.setBackgroundResource(R.mipmap.down);

                //popupWindow.dismiss();
                //image_pop.setBackgroundResource(R.mipmap.up);

                image_pop.setSelected(!image_pop.isSelected());
                //flag=!flag;
            }
        });
        popupWindow.dismiss();

    }

    private void initIntent() {
        Intent intent=getIntent();
        list = (List<ShoppBean.ResultBean>) intent.getSerializableExtra("creation_bill");
        creationRecyAdapter.setList(list);
        //算出数量和总价格
        setAllNumPrice(list);
    }
    private void setAllNumPrice(List<ShoppBean.ResultBean> list) {
        //这里不能break跳出，因为还有需要计算后面点击商品的价格和数量，所以必须跑完整个循环
        double totalPrice=0;
        //商品购买的数量
        int num=0;
        for (int i=0;i<list.size();i++){
            totalPrice=totalPrice+list.get(i).getPrice()*list.get(i).getCount();
            num=num+list.get(i).getCount();
        }
        text_allnum.setText(""+num);
        text_allprice.setText(""+totalPrice);
    }

    //互绑

    private void initIpresenter() {
        mIpresenterImpl=new CreationPresenter(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        mIpresenterImpl.deatch();
    }

    @Override
    public Context getActivitys() {
        return CreateActivity.this;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initTitle() {

    }


    public void  getShopData(Object object){
        if (object instanceof SelectAddressBean){
            SelectAddressBean selectAddressBean= (SelectAddressBean) object;
            List<SelectAddressBean.ResultBean> result = selectAddressBean.getResult();
            for (int i=0;i<result.size();i++){
                if (selectAddressBean.getResult().get(i).getWhetherDefault()==1){
                    text_name.setText(result.get(i).getRealName());
                    text_phone.setText(result.get(i).getPhone());
                    AddressListBean addressListBean = new Gson().fromJson(result.get(i).getAddress(), AddressListBean.class);
                    text_address.setText(addressListBean.getCity()+addressListBean.getAddress());
                    addressId=result.get(i).getId();
                }
            }
            Log.i("asadasd", "getViewData: "+result.size());
            popCreationAdapter.setList(result);
        }
    }

    public void getViewData(Object object) {
        if (object instanceof CreationShopBean){
            CreationShopBean creationShopBean= (CreationShopBean) object;
            if (creationShopBean.getStatus().equals("0000")){
                EventBus.getDefault().postSticky(new CreationMsgBean("creation","send"));
                Toast.makeText(this, creationShopBean.getMessage(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "创建失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void getDizhiData(Object object) {
        if (object instanceof DefaultBean){
            DefaultBean defaultBean= (DefaultBean) object;
            if (defaultBean.getStatus().equals("0000")){

            }
        }
    }

}

