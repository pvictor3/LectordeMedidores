package com.example.lectordemedidores.screens.login;

import com.example.lectordemedidores.screens.common.ObservableViewMvc;

public interface LoginViewMvc extends ObservableViewMvc<LoginViewMvc.Listener> {

    public interface Listener{
        void onLoginButtonClicked(String email, String password);

        void onForgotPasswordClicked();

        void onTextInputChanged();
    }

    void showProgressIndication();

    void hideProgressIndication();

    void setEnableLoginButton(boolean enable);

    void clearTexts();

    void showMessage(String message);

    void setEmailError();

    void setPasswordError();

    void clearErrors();
}
