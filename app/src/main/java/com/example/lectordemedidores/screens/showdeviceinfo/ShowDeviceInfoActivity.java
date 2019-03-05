package com.example.lectordemedidores.screens.showdeviceinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.lectordemedidores.common.Constants;
import com.example.lectordemedidores.devices.ConnectDeviceUseCase;
import com.example.lectordemedidores.screens.common.BaseActivity;

public class ShowDeviceInfoActivity extends BaseActivity implements ShowDeviceInfoViewMvc.Listener {

    public static void start(Context context, String address){
        Intent intent = new Intent(context, ShowDeviceInfoActivity.class);
        intent.putExtra(Constants.EXTRA_ADDRESS, address);
        context.startActivity(intent);
    }

    private ShowDeviceInfoViewMvc mViewMvc;
    private ConnectDeviceUseCase mConnectDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getShowDeviceInfoViewMvc(null);
        mViewMvc.registerListener(this);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.showProgressBar();
        mConnectDevice = new ConnectDeviceUseCase(getIntent().getStringExtra(Constants.EXTRA_ADDRESS), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnectDevice.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectDevice.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mConnectDevice.onStop(this);
    }

    @Override
    public void onModifyButtonClicked() {

    }

    @Override
    public void onConnectButtonClicked() {

    }

    @Override
    public void onValveSwitchClicked(boolean state) {

    }
}
