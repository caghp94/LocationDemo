package com.example.locationdemo.data.remote;


import com.example.locationdemo.data.remote.request.LocationRequest;
import com.example.locationdemo.data.remote.request.LoginRequest;
import com.example.locationdemo.data.remote.response.LoginResponse;
import com.example.locationdemo.data.remote.response.UpdateLocationResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;

public class FakeRemoteSource implements RemoteSource {

    @Override
    public Flowable<UpdateLocationResponse> updateLocation(@Body LocationRequest request) {
        return Flowable.just(new UpdateLocationResponse());
    }

    @Override
    public Flowable<Boolean> login() {
        return Flowable.just(true);
    }

    @Override
    public Flowable<Boolean> logout() {
        return Flowable.just(true);
    }
}
