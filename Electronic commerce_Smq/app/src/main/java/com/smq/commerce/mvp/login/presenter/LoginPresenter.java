package com.smq.commerce.mvp.login.presenter;

import com.smq.commerce.activity.LoginActivity;
import com.smq.commerce.api.Api;
import com.smq.commerce.mvp.login.model.ILoginModel;
import com.smq.commerce.mvp.login.model.LoginModel;

import java.util.Map;

/**
 * Time:2019/3/22
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class LoginPresenter implements ILoginPresenter{

    LoginActivity loginActivity;
    private final LoginModel loginModel;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        loginModel = new LoginModel();
    }

    @Override
    public void loginPre(Map<String, String> map) {
        loginModel.login( map, new ILoginModel.ILoginCallBack() {
            @Override
            public void onStatus(Object o) {
                loginActivity.showMsg(o);
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
