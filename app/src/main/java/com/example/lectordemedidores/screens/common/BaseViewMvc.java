package com.example.lectordemedidores.screens.common;

import android.content.Context;
import android.view.View;

public abstract class BaseViewMvc implements ViewMvc{
    View mRootView;

    public View getRootView() {
        return mRootView;
    }

    protected void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    protected Context getContext(){
        return mRootView.getContext();
    }

    protected <T extends View> T findViewById(int id){
        return mRootView.findViewById(id);
    }

}
