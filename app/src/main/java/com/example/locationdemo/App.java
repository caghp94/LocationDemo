package com.example.locationdemo;

import android.app.Application;

import com.example.locationdemo.data.AppComponent;
import com.example.locationdemo.data.AppModule;
import com.example.locationdemo.data.DaggerAppComponent;

public class App extends Application {


    private static App instance;
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupDaggerComponents();

    }

    public static App get(){
        return instance;
    }


    private void setupDaggerComponents(){
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent(){
        return component;
    }


}
