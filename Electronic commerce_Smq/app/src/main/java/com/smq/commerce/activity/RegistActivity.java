package com.smq.commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smq.commerce.R;
import com.smq.commerce.bean.RegistBean;
import com.smq.commerce.mvp.regist.presenter.RegistPresenter;
import com.smq.commerce.mvp.regist.view.RegistView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RegistActivity extends AppCompatActivity implements RegistView {


    @BindView(R.id.regist_phone)
    EditText registPhone;
    @BindView(R.id.regist_yzm)
    EditText registYzm;
    @BindView(R.id.regist_pwd)
    EditText registPwd;
    @BindView(R.id.text_login)
    TextView textLogin;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    private RegistPresenter registPresenter;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        bind = ButterKnife.bind(this);

        registPresenter = new RegistPresenter(this);
        registPresenter.attachView(this);

        //已有账号 返回登录页面
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                finish();
            }
        });

        //点击注册按钮
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map = new HashMap<>();
                String phone = registPhone.getText().toString();
                String pwd = registPwd.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(RegistActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    map.put("phone",phone);
                    map.put("pwd",pwd);
                    registPresenter.registPre(map);
                }
            }
        });
    }

    @Override
    public void showMsg(Object o) {
        RegistBean registBean = (RegistBean) o;
        String status = registBean.getStatus();
        if (status.equals("0000")) {
            Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(RegistActivity.this, registBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        registPresenter.deatchView();
    }
}

