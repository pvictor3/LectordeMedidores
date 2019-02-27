package com.example.lectordemedidores.devices;

public class Device {
    private final String mName;

    private final String mAddress;

    public Device(String name, String address) {
        this.mName = name;
        this.mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public String getAddress() {
        return mAddress;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Device){
            return this.mAddress.equals((((Device) obj).mAddress));
        }
        return false;
    }
}
