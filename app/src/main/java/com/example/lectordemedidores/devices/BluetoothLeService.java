package com.example.lectordemedidores.devices;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.UUID;

import static com.example.lectordemedidores.common.Constants.ACTION_DATA_AVAILABLE;
import static com.example.lectordemedidores.common.Constants.ACTION_GATT_CONNECTED;
import static com.example.lectordemedidores.common.Constants.ACTION_GATT_DISCONNECTED;
import static com.example.lectordemedidores.common.Constants.ACTION_GATT_SERVICES_DISCOVERED;
import static com.example.lectordemedidores.common.Constants.EXTRA_DATA;
import static com.example.lectordemedidores.common.Constants.SERVICE_UUID;
import static com.example.lectordemedidores.common.Constants.STATE_CONNECTED;
import static com.example.lectordemedidores.common.Constants.STATE_CONNECTING;
import static com.example.lectordemedidores.common.Constants.STATE_DISCONNECTED;
import static com.example.lectordemedidores.common.Constants.UUID_DATA;

public class BluetoothLeService extends Service {

    public static final String TAG = "BluetoothLeService";
    private final IBinder mBinder =new LocalBinder();
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private String mBluetoothDeviceAddress;
    private int mConnectionState = STATE_DISCONNECTED;

    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG, "onBind: ");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize(){
        if(mBluetoothManager == null){
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if(mBluetoothManager == null){
                Log.e(TAG, "initialize: Unable to initialize BtManager");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null){
            Log.e(TAG, "initialize: Unable to obtain a BluetoothAdapter");
            return false;
        }

        return true;
    }

    public boolean connect(String address){
        if(mBluetoothAdapter == null || address == null){
            Log.w(TAG, "connect: Bluetooth adapter not initialized or unspecified address");
            return false;
        }

        if(address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null){
            Log.d(TAG, "connect: Trying to use an existing mBluetoothGatt for connection");
            if (mBluetoothGatt.connect()){
                mConnectionState = STATE_CONNECTING;
                return true;
            }else{
                return false;
            }
        }

        final BluetoothDevice device =mBluetoothAdapter.getRemoteDevice(address);
        if(device == null){
            Log.w(TAG, "connect: Device not found. Unable to connect");
            return false;
        }

        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "connect: Trying to create a new connection");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    public void close(){
        if(mBluetoothGatt == null){
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    private void broadcastUpdate(final String action){
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // For all other profiles, writes the data formatted in HEX.
        final byte[] data = characteristic.getValue();
        if (data != null && data.length > 0) {
            //final StringBuilder stringBuilder = new StringBuilder(data.length);
            //for(byte byteChar : data)
            //stringBuilder.append(String.format("%02X ", byteChar));
            //intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
            intent.putExtra(EXTRA_DATA, new String(data));
            intent.putExtra(UUID_DATA, characteristic.getUuid().toString());
        }
        sendBroadcast(intent);
    }

    public BluetoothGattService getGattService(){
        if (mBluetoothGatt == null) {
            return null;
        }
        return mBluetoothGatt.getService(UUID.fromString(SERVICE_UUID));
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService(){
            return BluetoothLeService.this;
        }
    }

    public boolean readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        return mBluetoothGatt.readCharacteristic(characteristic);

    }

    private final BluetoothGattCallback mGattCallback= new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED){
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "onConnectionStateChange: Connected to gatt server");
                Log.i(TAG, "onConnectionStateChange: Trying to discover services");
                mBluetoothGatt.discoverServices();
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "onConnectionStateChange: Disconnected from GATT server");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onServicesDiscovered: ");
            if (status == BluetoothGatt.GATT_SUCCESS){
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d(TAG, "onCharacteristicRead: ");
            if(status == BluetoothGatt.GATT_SUCCESS){
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            Log.d(TAG, "onCharacteristicWrite: ");
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.d(TAG, "onCharacteristicChanged: ");
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(TAG, "onDescriptorWrite: ");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d(TAG, "onDescriptorRead: " + descriptor.getValue());
        }
    };
}
