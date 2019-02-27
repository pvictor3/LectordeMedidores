package com.example.lectordemedidores.screens.searchdevices;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lectordemedidores.R;
import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.screens.common.BaseObservableViewMvc;

public class SearchDevicesItemViewMvcImpl extends BaseObservableViewMvc<SearchDevicesItemViewMvc.Listener>
    implements SearchDevicesItemViewMvc{
    private TextView mName;
    private TextView mAddress;
    private Device mDevice;

    public SearchDevicesItemViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_search_devices_item, parent, false));
        mName = findViewById(R.id.txt_device_name);
        mAddress = findViewById(R.id.txt_device_address);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners()){
                    listener.onDeviceClicked(mDevice);
                }
            }
        });
    }

    @Override
    public void bindDevice(Device device) {
        mDevice = device;
        mName.setText(device.getName());
        mAddress.setText(device.getAddress());
    }
}
