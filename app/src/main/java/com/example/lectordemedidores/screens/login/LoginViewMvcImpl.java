package com.example.lectordemedidores.screens.login;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lectordemedidores.R;
import com.example.lectordemedidores.screens.common.BaseObservableViewMvc;

public class LoginViewMvcImpl extends BaseObservableViewMvc<LoginViewMvc.Listener>
        implements LoginViewMvc{

    private ProgressBar mProgressBar;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mLogin;
    private TextView mForgotPassword;
    private EditText mEditTxtEmail;
    private EditText mEditTxtPass;

    public LoginViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_login, container, false));

        mProgressBar = findViewById(R.id.progressBar_login);
        mEmail = findViewById(R.id.til_txt_email_login);
        mPassword = findViewById(R.id.til_txt_pass_login);
        mLogin = findViewById(R.id.button_login);
        mForgotPassword = findViewById(R.id.button_forgot_password);
        mEditTxtEmail = findViewById(R.id.txt_email_login);
        mEditTxtPass = findViewById(R.id.txt_pass_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getListeners()){
                    listener.onLoginButtonClicked(mEmail.getEditText().getText().toString(),
                                                    mPassword.getEditText().getText().toString());
                }
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()){
                    listener.onForgotPasswordClicked();
                }
            }
        });

        mEditTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(Listener listener : getListeners()){
                    listener.onTextInputChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (Listener listener : getListeners()){
                    listener.onTextInputChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void showProgressIndication() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEnableLoginButton(boolean enable) {
        mLogin.setEnabled(enable);
    }

    @Override
    public void clearTexts() {
        mEmail.getEditText().setText("");
        mPassword.getEditText().setText("");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEmailError() {
        mEmail.setError("Email inválido");
    }

    @Override
    public void setPasswordError() {
        mPassword.setError("Password inválido");
    }

    @Override
    public void clearErrors(){
        mEmail.setError(null);
        mPassword.setError(null);
    }
}
