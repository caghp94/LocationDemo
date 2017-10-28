package com.example.locationdemo.data;

import android.app.Application;

import com.example.locationdemo.data.persistence.AppDao;
import com.example.locationdemo.data.persistence.SharedPrefAppDao;
import com.example.locationdemo.data.remote.FakeRemoteSource;
import com.example.locationdemo.data.remote.FirebaseRemoteSource;
import com.example.locationdemo.data.remote.RemoteSource;
import com.example.locationdemo.utils.GPSTracker;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    AppDao provideDao(){
        return new SharedPrefAppDao(application);
    }

    @Provides
    RemoteSource provideRemoteSource(){
        return new FirebaseRemoteSource();
    }

    @Provides
    GPSTracker providesGPS(){
        return new GPSTracker(application, null);
    }

}
