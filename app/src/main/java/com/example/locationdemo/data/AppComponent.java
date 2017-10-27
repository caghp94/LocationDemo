package com.example.locationdemo.data;

import com.example.locationdemo.LocationViewModel;
import com.example.locationdemo.LoginActivity;

import dagger.Component;

@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(AppRepository appRepository);
    void inject(LocationViewModel locationViewModel);
}
