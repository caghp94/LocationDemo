package com.upc.location;

import android.app.Application;

import com.upc.location.data.AppComponent;
import com.upc.location.data.AppModule;
import com.upc.location.data.DaggerAppComponent;

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
