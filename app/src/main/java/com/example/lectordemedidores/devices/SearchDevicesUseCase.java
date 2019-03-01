package com.example.lectordemedidores.devices;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.lectordemedidores.common.BaseObservable;
import com.example.lectordemedidores.common.Constants;

import java.util.List;

public class SearchDevicesUseCase extends BaseObservable<SearchDevicesUseCase.Listener> {

    private static final String TAG = "SearchDevicesUseCase";

    public interface Listener{
        void onDevicesFound(Device device);
        void onDevicesNotFound(int errorCode);
    }

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private BtleScanCallback btleScanCallback;
    private Handler mHandler;
    private boolean scanning;

    private class BtleScanCallback extends ScanCallback{

        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            notifySuccess(result);
            Log.d(TAG, "onScanResult: Dispositivo encontrado");
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {

        }

        @Override
        public void onScanFailed(int errorCode) {
            notifyFailure(errorCode);
        }

    }

    public SearchDevicesUseCase(Activity activity) {
        final BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void searchDevicesAndNotify(){
        Log.d(TAG, "searchDevicesAndNotify: Buscando dispositivos");
        btleScanCallback = new BtleScanCallback();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(btleScanCallback);
        scanning = true;
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(scanning){
                    bluetoothLeScanner.stopScan(btleScanCallback);
                    btleScanCallback = null;
                    scanning = false;
                    mHandler = null;
                    Log.d(TAG, "run: Deteniendo scaneo");
                }
            }
        }, Constants.DELAY_MILLIS);
    }

    public void stopScan(){
        if(scanning){
            bluetoothLeScanner.stopScan(btleScanCallback);
            btleScanCallback = null;
            scanning = false;
            mHandler = null;
            Log.d(TAG, "stopScan: Deteniendo scaneo");
        }
    }

    private void notifySuccess(ScanResult result){
        for(Listener listener : getListeners()){
            listener.onDevicesFound(new Device(
                    result.getDevice().getName(),
                    result.getDevice().getAddress()
            ));
        }
    }

    private void notifyFailure(int errorCode){
        for(Listener listener : getListeners()){
            listener.onDevicesNotFound(errorCode);
        }
    }
}
