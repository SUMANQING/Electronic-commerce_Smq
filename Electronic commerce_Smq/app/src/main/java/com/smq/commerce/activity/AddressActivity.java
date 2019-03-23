package com.smq.commerce.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.ppw.CityWheelPickerPopupWindow;
import com.smq.commerce.R;
import com.smq.commerce.bean.InsertAddress;
import com.smq.commerce.mvp.insertadd.presenter.AddadressPresenter;
import com.smq.commerce.mvp.insertadd.view.IAddrView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity implements IAddrView {


    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.real_name)
    EditText realName;
    @BindView(R.id.phone_num)
    EditText phoneNum;
    @BindView(R.id.address_text)
    EditText addressText;
    @BindView(R.id.address_xiang)
    EditText addressXiang;
    @BindView(R.id.zip_code)
    EditText zipCode;
    @BindView(R.id.save_address)
    Button saveAddress;
    private AddadressPresenter addadressPresenter;
    private CityWheelPickerPopupWindow pickerPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addadress);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        final String userId = preferences.getString("userId", "");
        final String sessionId = preferences.getString("sessionId", "");

        addadressPresenter = new AddadressPresenter(this);


        pickerPopupWindow = new CityWheelPickerPopupWindow(AddressActivity.this);
        pickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
            @Override
            public void onSelected(String s, String s1, String s2, String s3) {
                addressText.setText(s + " " + s1 + " " + s2);
                zipCode.setText(s3);
            }
        });

        addressText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    pickerPopupWindow.show();
                }
            }
        });

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String real = realName.getText().toString();
                String phone = phoneNum.getText().toString();
                String address = addressText.getText().toString();
                String addressX = addressXiang.getText().toString();
                String zipcode = zipCode.getText().toString();
                String xiang = address + addressX;
                addadressPresenter.getAddPresenter(userId, sessionId, real, phone, xiang, zipcode);
            }
        });
    }

    @Override
    public void getAddrViewData(Object o) {
        InsertAddress insertAddress = (InsertAddress) o;
        if (insertAddress.getStatus().equals("0000")) {
            Toast.makeText(this, insertAddress.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, insertAddress.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

