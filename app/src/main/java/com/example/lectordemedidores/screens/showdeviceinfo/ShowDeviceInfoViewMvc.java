package com.example.lectordemedidores.screens.showdeviceinfo;

import com.example.lectordemedidores.devices.DeviceInfo;
import com.example.lectordemedidores.screens.common.ObservableViewMvc;

public interface ShowDeviceInfoViewMvc extends ObservableViewMvc<ShowDeviceInfoViewMvc.Listener> {
    public interface Listener{
        void onModifyButtonClicked();
        void onConnectButtonClicked();
        void onValveSwitchClicked(boolean state);
    }

    void bindDeviceInfo(DeviceInfo deviceInfo);

    void showStateConnection(int state);

    void enableConnectButton(boolean enable);

    void showProgressBar();

    void hideProgressBar();
}
