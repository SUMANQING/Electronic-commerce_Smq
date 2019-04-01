package com.smq.commerce.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smq.commerce.R;
import com.smq.commerce.adapter.add.MyAddressAdapter;
import com.smq.commerce.bean.AddressList;
import com.smq.commerce.mvp.findadd.presenter.FindAddressPresenter;
import com.smq.commerce.mvp.findadd.view.IFindAddressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity implements IFindAddressView {


    @BindView(R.id.text_add)
    TextView textAdd;
    @BindView(R.id.text_finish)
    TextView textFinish;
    @BindView(R.id.relative_add)
    RelativeLayout relativeAdd;
    @BindView(R.id.address_recycler_view)
    RecyclerView addressRecyclerView;
    @BindView(R.id.add_address)
    Button addAddress;
    private FindAddressPresenter findAddressPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = preferences.getString("userId", "");
        String sessionId = preferences.getString("sessionId", "");

        findAddressPresenter = new FindAddressPresenter(AddActivity.this);
        findAddressPresenter.getFindPresenter(userId,sessionId);
        findAddressPresenter.attachView(this);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,AddressActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void getFindView(Object o) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        addressRecyclerView.setLayoutManager(linearLayoutManager);
        AddressList addressList = (AddressList) o;
        Log.i("123", "getFindView: "+addressList.getResult().get(0).getAddress());
        List<AddressList.ResultBean> resultBeanList = addressList.getResult();
        MyAddressAdapter addressAdapter = new MyAddressAdapter(this,resultBeanList);
        addressRecyclerView.setAdapter(addressAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        findAddressPresenter.deatchView();
    }
}

