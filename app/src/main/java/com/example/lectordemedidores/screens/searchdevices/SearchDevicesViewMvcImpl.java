package com.example.lectordemedidores.screens.searchdevices;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lectordemedidores.R;
import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.screens.common.BaseObservableViewMvc;
import com.example.lectordemedidores.screens.common.ViewMvcFactory;

import java.util.List;

public class SearchDevicesViewMvcImpl extends BaseObservableViewMvc<SearchDevicesViewMvc.Listener>
    implements SearchDevicesViewMvc, SearchRecyclerAdapter.Listener{
    private RecyclerView mRecyclerSearch;
    private SearchRecyclerAdapter mAdapter;
    private ProgressBar progressBar;

    public SearchDevicesViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.layout_search_devices, parent, false));

        progressBar = findViewById(R.id.progressBar);
        mRecyclerSearch = findViewById(R.id.recycler_search_devices);
        mRecyclerSearch.setLayoutManager( new LinearLayoutManager(getContext()));
        mAdapter = new SearchRecyclerAdapter(this, viewMvcFactory);
        mRecyclerSearch.setAdapter(mAdapter);
    }

    @Override
    public void bindDevices(Device device) {
        mAdapter.bindDevice(device);
    }

    @Override
    public void showProgressIndication() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressIndication() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeviceClicked(Device device) {
        for (Listener listener : getListeners()){
            listener.onDeviceClicked(device);
        }
    }
}
