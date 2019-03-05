package com.example.lectordemedidores.common;

public class Constants {
    public static final int DELAY_MILLIS = 10000;

    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;

    public static final String EXTRA_ADDRESS = "DEVICE ADDRESS";
    public static final String EXTRA_DATA = "EXTRA DATA";
    public static final String UUID_DATA = "UUID_DATA";
    public static final String SERVICE_UUID = ""; // TODO: 04/03/2019
    public static final String COUNTER_UUID = ""; // TODO: 04/03/2019

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
}
