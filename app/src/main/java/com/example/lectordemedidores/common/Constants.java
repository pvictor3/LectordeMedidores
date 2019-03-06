package com.example.lectordemedidores.common;

public class Constants {
    public static final int DELAY_MILLIS = 10000;

    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECTED = 2;

    public static final String EXTRA_ADDRESS = "DEVICE ADDRESS";
    public static final String EXTRA_DATA = "EXTRA DATA";
    public static final String UUID_DATA = "UUID_DATA";
    public static final String SERVICE_UUID = "32c64438-23c1-40e3-8a85-2dddc120e432";
    public static final String COUNTER_UUID = "3edde8fa-1ab3-4162-b53f-42884f1f6714";
    public static final String BATTERY_UUID = "ae994543-4583-4cd4-aa97-692ca9bfa711";
    public static final String VALVE_UUID = "d752ffc7-0123-4932-92a6-920bc7852bcd";
    public static final String DEVICENUMBER_UUID = "e0583abd-28a7-4c2a-8b7b-f156e2394ec4";
    public static final String BRAND_UUID = "af815952-d651-496a-9942-7e40684ff2ed";

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
}
