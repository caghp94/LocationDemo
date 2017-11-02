package com.upc.location.data;

import android.app.Application;

import com.upc.location.data.persistence.AppDao;
import com.upc.location.data.remote.FirebaseRemoteSource;
import com.upc.location.data.remote.RemoteSource;
import com.upc.location.utils.GPSTracker;
import com.upc.location.data.persistence.SharedPrefAppDao;

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
