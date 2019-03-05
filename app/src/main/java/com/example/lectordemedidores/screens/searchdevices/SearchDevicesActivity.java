package com.example.lectordemedidores.screens.searchdevices;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.devices.SearchDevicesUseCase;
import com.example.lectordemedidores.screens.common.BaseActivity;
import com.example.lectordemedidores.screens.showdeviceinfo.ShowDeviceInfoActivity;

public class SearchDevicesActivity extends BaseActivity
        implements SearchDevicesViewMvcImpl.Listener, SearchDevicesUseCase.Listener{
    private static final int REQUEST_ENABLE_BT = 1000;
    private static final int REQUEST_FINE_LOCATION = 1001;
    private static final String TAG = "SearchDevicesActivity";

    public static void start(Context context){
        Intent intent = new Intent(context, SearchDevicesActivity.class);
        context.startActivity(intent);
    }

    private SearchDevicesViewMvc mViewMvc;

    private SearchDevicesUseCase searchDevicesUseCase;

    private boolean mScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchDevicesUseCase = getCompositionRoot().getSearchDevicesUseCase();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSearchDevicesViewMvc(null);
        mViewMvc.registerListener(this);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchDevicesUseCase.registerListener(this);
        mViewMvc.showProgressIndication();
        if(!hasPermissions() && mScanning){
            return;
        }
        searchDevicesUseCase.searchDevicesAndNotify();
        Log.d(TAG, "onStart: Terminando ONSTART");
    }

    private boolean hasPermissions() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return false;
        }else if(!hasLocationPermission()){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
            return false;
        }
        return true;
    }

    private boolean hasLocationPermission() {
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchDevicesUseCase.stopScan();
        searchDevicesUseCase.unregisterListener(this);
        Log.d(TAG, "onStop: Hola Mundo!");
    }

    @Override
    public void onDeviceClicked(Device device) {
        //Conectar con dispositivo - activty
        mViewMvc.showMessage("Conectando con el dispositivo");
        ShowDeviceInfoActivity.start(this, device.getAddress());
    }

    @Override
    public void onDevicesFound(Device device) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindDevices(device);
    }

    @Override
    public void onDevicesNotFound(int errorCode) {
        mViewMvc.hideProgressIndication();
        mViewMvc.showMessage("Error al buscar dispositivos: " + errorCode);
    }
}
