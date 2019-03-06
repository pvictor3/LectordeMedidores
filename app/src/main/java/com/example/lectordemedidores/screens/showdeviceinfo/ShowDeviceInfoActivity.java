package com.example.lectordemedidores.screens.showdeviceinfo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;

import com.example.lectordemedidores.common.Constants;
import com.example.lectordemedidores.devices.BluetoothLeService;
import com.example.lectordemedidores.devices.ConnectDeviceUseCase;
import com.example.lectordemedidores.devices.DeviceInfo;
import com.example.lectordemedidores.screens.common.BaseActivity;

import java.util.HashMap;

public class ShowDeviceInfoActivity extends BaseActivity implements ShowDeviceInfoViewMvc.Listener,
    ConnectDeviceUseCase.Listener{

    public static final String TAG = "ShowDeviceInfoActivity";

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

        mConnectDevice = getCompositionRoot().getConnectDeviceUseCase();
        mConnectDevice.registerListener(this);
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.enableConnectButton(false);
        mViewMvc.showProgressBar();
        mViewMvc.hideDeviceInfo();
        mConnectDevice.connectDeviceAndNotify(getIntent().getStringExtra(Constants.EXTRA_ADDRESS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnectDevice.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectDevice.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mConnectDevice.onStop();
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

    @Override
    public void onServiceConnectedFailure() {
        mViewMvc.hideProgressBar();
    }

    @Override
    public void onGattConnecting() {
        mViewMvc.showStateConnection(Constants.STATE_CONNECTING);
    }
    @Override
    public void onGattConnected() {
        mViewMvc.hideProgressBar();
        mViewMvc.showStateConnection(Constants.STATE_CONNECTED);
    }

    @Override
    public void onGattDisconnected() {
        mViewMvc.hideProgressBar();
        mViewMvc.enableConnectButton(true);
        mViewMvc.showStateConnection(Constants.STATE_DISCONNECTED);
    }

    @Override
    public void onCharacReadComplete(DeviceInfo deviceInfo) {
        mViewMvc.showDeviceInfo();
        mViewMvc.bindDeviceInfo(deviceInfo);
    }

}
