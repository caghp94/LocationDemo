package com.example.locationdemo.data.remote;

import com.example.locationdemo.data.remote.request.*;
import com.example.locationdemo.data.remote.response.*;


import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RemoteSource {

    Flowable<UpdateLocationResponse> updateLocation(LocationRequest request);

    Flowable<Boolean> login();

    Flowable<Boolean> logout();
}
