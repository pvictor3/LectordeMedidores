package com.example.lectordemedidores.screens.login;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;

import com.example.lectordemedidores.screens.common.BaseActivity;
import com.example.lectordemedidores.screens.searchdevices.SearchDevicesActivity;

public class LoginActivity extends BaseActivity implements LoginViewMvcImpl.Listener {
    private LoginViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getLoginViewMvc(null);
        mViewMvc.registerListener(this);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.hideProgressIndication();
    }

    @Override
    public void onLoginButtonClicked(String email, String password) {
        if (!isValidEmail(email)){
            mViewMvc.setEmailError();
        }else if(!isValidPassword(password)){
            mViewMvc.setPasswordError();
        }else{
            mViewMvc.showMessage("Iniciando sesion");
            mViewMvc.showProgressIndication();
            //mViewMvc.setEnableLoginButton(false);
            SearchDevicesActivity.start(this);
        }
    }

    @Override
    public void onForgotPasswordClicked() {
        mViewMvc.showMessage("Creando nueva cuenta");
    }

    @Override
    public void onTextInputChanged() {
        mViewMvc.clearErrors();
    }

    private boolean isValidEmail(String email){
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return !password.isEmpty();
    }
}
