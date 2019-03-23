package com.smq.commerce.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smq.commerce.R;
import com.smq.commerce.bean.LoginBean;
import com.smq.commerce.frag.Frag_home;
import com.smq.commerce.mvp.login.presenter.LoginPresenter;
import com.smq.commerce.mvp.login.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginView {


    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_check)
    CheckBox loginCheck;
    @BindView(R.id.login_registered)
    TextView registeredButton;
    @BindView(R.id.login_button)
    Button loginButton;
    private SharedPreferences preferences;
    private LoginPresenter loginPresenter;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind = ButterKnife.bind(this);

        preferences = getSharedPreferences("config", MODE_PRIVATE);
        boolean flag = preferences.getBoolean("flag", false);
        loginCheck.setChecked(flag);
        if (flag) {
            String name = preferences.getString("name", "");
            String pwd = preferences.getString("pwd", "");
            loginPhone.setText(name);
            loginPwd.setText(pwd);
        }

        loginPresenter = new LoginPresenter(this);
        final Map<String, String> map = new HashMap<>();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = loginPhone.getText().toString();
                String pwd = loginPwd.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("phone", phone);
                    map.put("pwd", pwd);
                    if (loginPresenter != null) {
                        loginPresenter.loginPre(map);
                    }

                    SharedPreferences.Editor edit = preferences.edit();
                    if (loginCheck.isChecked()) {
                        edit.putBoolean("flag", true);
                        edit.putString("name", phone);
                        edit.putString("pwd", pwd);
                    } else {
                        edit.putBoolean("flag", false);
                    }
                    edit.commit();
                }
            }
        });

        registeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
            }
        });
    }

    @Override
    public void showMsg(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            //System.out.println(loginBean.getMessage());
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String status = loginBean.getStatus();
            if (status.equals("0000")) {
                Log.i("123", "showMsg: " + loginBean.getResult().getUserId());
                editor.putString("userId", loginBean.getResult().getUserId() + "");
                editor.putString("sessionId", loginBean.getResult().getSessionId() + "");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void jumpActivity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}

