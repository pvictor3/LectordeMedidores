package com.example.lectordemedidores.common.dependencyinjection;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.lectordemedidores.devices.SearchDevicesUseCase;
import com.example.lectordemedidores.screens.common.ViewMvcFactory;

public class ControllerCompositionRoot {
    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, Activity mActivity) {
        this.mCompositionRoot = mCompositionRoot;
        this.mActivity = mActivity;
    }

    private LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater());
    }

    public SearchDevicesUseCase getSearchDevicesUseCase(){
        return new SearchDevicesUseCase(mActivity);
    }

}
