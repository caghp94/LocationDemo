package com.upc.location.data;

import com.upc.location.LocationViewModel;

import dagger.Component;

@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(AppRepository appRepository);
    void inject(LocationViewModel locationViewModel);
}
