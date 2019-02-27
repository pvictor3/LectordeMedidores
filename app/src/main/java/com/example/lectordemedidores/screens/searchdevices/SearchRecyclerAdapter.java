package com.example.lectordemedidores.screens.searchdevices;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.lectordemedidores.devices.Device;
import com.example.lectordemedidores.screens.common.ViewMvcFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>
    implements SearchDevicesItemViewMvc.Listener{

    public interface Listener{
        void onDeviceClicked(Device device);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final SearchDevicesItemViewMvc mViewMvc;

        public ViewHolder(SearchDevicesItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            this.mViewMvc = viewMvc;
        }
    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;
    private ArrayList<Device> mDevices;

    public SearchRecyclerAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
        mDevices = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        SearchDevicesItemViewMvc viewMvc = mViewMvcFactory.getSearchDevicesItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new ViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mViewMvc.bindDevice(mDevices.get(i));
    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    @Override
    public void onDeviceClicked(Device device) {
        mListener.onDeviceClicked(device);
    }

    public void bindDevice(Device device){
        if(!mDevices.contains(device)){
            mDevices.add(device);
            notifyDataSetChanged();
        }

    }
}
