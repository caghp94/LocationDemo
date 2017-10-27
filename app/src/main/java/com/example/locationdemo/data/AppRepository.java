package com.example.locationdemo.data;

import android.support.annotation.NonNull;

import com.example.locationdemo.App;
import com.example.locationdemo.data.persistence.AppDao;
import com.example.locationdemo.data.remote.RemoteSource;
import com.example.locationdemo.data.remote.request.LocationRequest;
import com.example.locationdemo.data.remote.request.LoginRequest;
import com.example.locationdemo.data.remote.response.LoginResponse;
import com.example.locationdemo.data.remote.response.UpdateLocationResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class AppRepository {

    @Inject
    RemoteSource remoteSource;

    @Inject
    AppDao appDao;

    public AppRepository(){
        App.get().getComponent().inject(this);
    }


    public Flowable<LoginResponse> login(@NonNull final String username, @NonNull final String password){
        if (!username.contains("@"))
            return Flowable.just(new LoginResponse(false, "Email inválido"));

        if(password.length() < 4)
            return Flowable.just(new LoginResponse(false, "Tu contraseña debe tener al menos 4 caracteres"));

        return remoteSource.login(new LoginRequest(username, password))
                .doOnNext(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(@NonNull LoginResponse loginResponse) throws Exception {
                        if (loginResponse.isSuccess())
                            appDao.saveSession(username, password);
                    }
                });
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


}
