package com.example.lectordemedidores.devices;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.lectordemedidores.common.BaseObservable;
import com.example.lectordemedidores.common.Constants;

import java.util.HashMap;
import java.util.List;


import static android.content.Context.BIND_AUTO_CREATE;

public class ConnectDeviceUseCase extends BaseObservable<ConnectDeviceUseCase.Listener> {

    public static final String TAG = "ConnectDeviceUseCase";
    private BluetoothLeService mBluetoothLeService;

    public interface Listener{
        void onServiceConnectedFailure();
    }

    private String mAddress;
    private HashMap<String,String> charValues;
    private List<BluetoothGattCharacteristic> characteristics;
    private BluetoothGattCharacteristic counterCharacteristic;



    public ConnectDeviceUseCase(String address, Context context) {
        mAddress = address;
        Intent gattServiceIntent = new Intent(context, BluetoothLeService.class);
        context.bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public void onResume(Activity activity){
        activity.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        Log.d(TAG, "onResume: BroadcastReceiver registrado!!!");
        if(mBluetoothLeService != null){
            final boolean result = mBluetoothLeService.connect(mAddress);
            Log.d(TAG, "onResume: Connect request result = " + result);
        }
    }

    public void onPause(Activity activity) {
        Log.d(TAG, "onPause: ");
        activity.unregisterReceiver(mGattUpdateReceiver);
    }

    public void onStop(Activity activity) {
        Log.d(TAG, "onStop: ");
        activity.unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if(!mBluetoothLeService.initialize()){
                Log.e(TAG, "onServiceConnected: Unable to initialize Bluetooth");
                for(Listener listener : getListeners()){
                    listener.onServiceConnectedFailure();
                }
            }
            mBluetoothLeService.connect(mAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(Constants.ACTION_GATT_CONNECTED.equals(action)){
                // TODO: 04/03/2019
            }else if(Constants.ACTION_GATT_DISCONNECTED.equals(action)){
                // TODO: 04/03/2019
            }else if(Constants.ACTION_GATT_SERVICES_DISCOVERED.equals(action)){
                charValues = new HashMap<>();
                BluetoothGattService service = mBluetoothLeService.getGattService();
                characteristics = service.getCharacteristics();
                for(BluetoothGattCharacteristic characteristic : characteristics){
                    if(characteristic.getUuid().toString().equals(Constants.COUNTER_UUID)){
                        Log.d(TAG, "onReceive: Properties of Counter " + characteristic.getProperties());
                        counterCharacteristic = characteristic;
                    }
                }
                requestCharacteristic();
            }else if(Constants.ACTION_DATA_AVAILABLE.equals(action)){
                Log.d(TAG, "onReceive: ACTION_DATA_AVAILABLE");
                Log.d(TAG, "onReceive: " + intent.getStringExtra(Constants.UUID_DATA)
                        + " : " + intent.getStringExtra(Constants.EXTRA_DATA));
                charValues.put(intent.getStringExtra(Constants.UUID_DATA),
                        intent.getStringExtra(Constants.EXTRA_DATA));
                characteristics.remove(characteristics.size() - 1 );
                if(characteristics.size() > 0){
                    requestCharacteristic();
                }else{
                    Toast.makeText(context, "LECTURA COMPLETA DE CHARACTERISTICAS", Toast.LENGTH_LONG).show();
                    // TODO: 04/03/2019
                }
            }
        }
    };

    private void requestCharacteristic(){
        mBluetoothLeService.readCharacteristic(characteristics.get(characteristics.size() - 1));
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_GATT_CONNECTED);
        intentFilter.addAction(Constants.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(Constants.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(Constants.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
