package com.example.lectordemedidores.devices;

public class DeviceInfo {
    private final String counter;
    private final String battery;
    private final String valve;
    private final String deviceNumber;
    private final String brand;

    public DeviceInfo(String counter, String battery, String valve, String deviceNumber, String brand) {
        this.counter = counter;
        this.battery = battery;
        this.valve = valve;
        this.deviceNumber = deviceNumber;
        this.brand = brand;
    }

    public String getCounter() {
        return counter;
    }

    public String getBattery() {
        return battery;
    }

    public String getValve() {
        return valve;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public String getBrand() {
        return brand;
    }
}
