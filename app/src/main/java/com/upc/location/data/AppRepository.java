package com.upc.location.data;

import android.support.annotation.NonNull;

import com.upc.location.App;
import com.upc.location.data.persistence.AppDao;
import com.upc.location.data.remote.RemoteSource;
import com.upc.location.data.remote.request.LocationRequest;
import com.upc.location.data.remote.response.UpdateLocationResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class AppRepository {

    @Inject
    RemoteSource remoteSource;

    @Inject
    AppDao appDao;

    public AppRepository(){
        App.get().getComponent().inject(this);
    }

    public Flowable<Boolean> updateLocation(double latitude, double longitude){
        LocationRequest request = new LocationRequest(latitude, longitude);
        return remoteSource.updateLocation(request).map(new Function<UpdateLocationResponse, Boolean>() {
            @Override
            public Boolean apply(@NonNull UpdateLocationResponse updateLocationResponse) throws Exception {
                return true;
            }
        });
    }

    public void login(){
        remoteSource.login();
    }

    public void logout(){
        remoteSource.logout();
    }
}
