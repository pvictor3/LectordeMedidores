package com.example.lectordemedidores.screens.showdeviceinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lectordemedidores.R;
import com.example.lectordemedidores.common.Constants;
import com.example.lectordemedidores.devices.DeviceInfo;
import com.example.lectordemedidores.screens.common.BaseObservableViewMvc;

public class ShowDeviceInfoViewMvcImpl extends BaseObservableViewMvc<ShowDeviceInfoViewMvc.Listener>
    implements ShowDeviceInfoViewMvc{

    private TextView connectionState;
    private TextView deviceNumber;
    private TextView brand;
    private TextView counter;
    private TextView battery;
    private ProgressBar progressBar;
    private EditText newCounter;
    private Switch valve;
    private Button connectBtn;
    private Button modifyBtn;

    public ShowDeviceInfoViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_show_device_info, container, false));
        connectionState = findViewById(R.id.textView_state_connected);
        deviceNumber = findViewById(R.id.textView_device_number);
        brand = findViewById(R.id.textView_brand);
        counter = findViewById(R.id.textView_counter);
        battery = findViewById(R.id.textView_battery);
        progressBar = findViewById(R.id.progress_bar_device_info);
        newCounter = findViewById(R.id.editText_new_counter);
        valve = findViewById(R.id.switch_valve);
        connectBtn = findViewById(R.id.button_connect);
        modifyBtn = findViewById(R.id.button_modify);

        valve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getListeners()){
                    listener.onValveSwitchClicked(valve.isChecked());
                }
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getListeners()){
                    listener.onConnectButtonClicked();
                }
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener : getListeners()){
                    listener.onModifyButtonClicked();
                }
            }
        });
    }

    @Override
    public void hideDeviceInfo() {
        deviceNumber.setVisibility(View.GONE);
        brand.setVisibility(View.GONE);
        counter.setVisibility(View.GONE);
        battery.setVisibility(View.GONE);
        valve.setVisibility(View.GONE);
        newCounter.setVisibility(View.GONE);
        modifyBtn.setVisibility(View.GONE);
    }

    @Override
    public void showDeviceInfo() {
        deviceNumber.setVisibility(View.VISIBLE);
        brand.setVisibility(View.VISIBLE);
        counter.setVisibility(View.VISIBLE);
        battery.setVisibility(View.VISIBLE);
        valve.setVisibility(View.VISIBLE);
        newCounter.setVisibility(View.VISIBLE);
        modifyBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindDeviceInfo(DeviceInfo deviceInfo) {
        deviceNumber.setText(deviceInfo.getDeviceNumber());
        brand.setText(deviceInfo.getBrand());
        counter.setText(deviceInfo.getCounter());
        battery.setText(deviceInfo.getBattery());
        if(deviceInfo.getValve() != null && deviceInfo.getValve().equals("Abierta")){
            valve.setChecked(true);
        }else{
            valve.setChecked(false);
        }
    }

    @Override
    public void showStateConnection(int state) {
        switch(state){
            case Constants.STATE_DISCONNECTED:
                connectionState.setText("Desconectado");
                break;
            case Constants.STATE_CONNECTING:
                connectionState.setText("Conectando");
                break;
            case Constants.STATE_CONNECTED:
                connectionState.setText("Conectado");
                break;
        }
    }

    @Override
    public void enableConnectButton(boolean enable) {
        connectBtn.setEnabled(enable);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
