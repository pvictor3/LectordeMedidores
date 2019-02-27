package com.example.lectordemedidores.screens.searchdevices;

import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.screens.common.ObservableViewMvc;

import java.util.List;

public interface SearchDevicesViewMvc extends ObservableViewMvc<SearchDevicesViewMvc.Listener> {
    interface Listener{
        void onDeviceClicked(Device device);
    }

    void bindDevices(Device device);

    void showProgressIndication();

    void hideProgressIndication();

    void showMessage(String message);
}
