package com.example.lectordemedidores.screens.common;

import android.support.v7.app.AppCompatActivity;

import com.example.lectordemedidores.common.dependencyinjection.ControllerCompositionRoot;
import com.example.lectordemedidores.screens.CustomApplication;

public class BaseActivity extends AppCompatActivity {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){
        if(mControllerCompositionRoot == null){
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mControllerCompositionRoot;
    }
}
