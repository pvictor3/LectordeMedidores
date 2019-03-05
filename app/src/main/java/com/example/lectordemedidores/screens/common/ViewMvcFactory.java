package com.example.lectordemedidores.screens.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.lectordemedidores.screens.login.LoginViewMvc;
import com.example.lectordemedidores.screens.login.LoginViewMvcImpl;
import com.example.lectordemedidores.screens.searchdevices.SearchDevicesItemViewMvc;
import com.example.lectordemedidores.screens.searchdevices.SearchDevicesItemViewMvcImpl;
import com.example.lectordemedidores.screens.searchdevices.SearchDevicesViewMvc;
import com.example.lectordemedidores.screens.searchdevices.SearchDevicesViewMvcImpl;
import com.example.lectordemedidores.screens.showdeviceinfo.ShowDeviceInfoViewMvc;
import com.example.lectordemedidores.screens.showdeviceinfo.ShowDeviceInfoViewMvcImpl;

public class ViewMvcFactory {
    private final LayoutInflater inflater;

    public ViewMvcFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public LoginViewMvc getLoginViewMvc(@Nullable ViewGroup parent){
        return new LoginViewMvcImpl(inflater, parent);
    }

    public SearchDevicesViewMvc getSearchDevicesViewMvc(@Nullable ViewGroup parent){
        return new SearchDevicesViewMvcImpl(inflater, parent, this);
    }

    public SearchDevicesItemViewMvc getSearchDevicesItemViewMvc(@Nullable ViewGroup parent) {
        return new SearchDevicesItemViewMvcImpl(inflater, parent);
    }

    public ShowDeviceInfoViewMvc getShowDeviceInfoViewMvc(@Nullable ViewGroup parent){
        return new ShowDeviceInfoViewMvcImpl(inflater, parent);
    }
}
