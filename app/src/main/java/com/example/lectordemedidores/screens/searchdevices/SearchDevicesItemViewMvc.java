package com.example.lectordemedidores.screens.searchdevices;

import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.screens.common.ObservableViewMvc;

public interface SearchDevicesItemViewMvc extends ObservableViewMvc<SearchDevicesItemViewMvc.Listener> {
    interface Listener{
        void onDeviceClicked(Device device);
    }

    void bindDevice(Device device);
}
