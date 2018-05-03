package com.upc.location.data.remote;


import com.upc.location.data.remote.request.LocationRequest;
import com.upc.location.data.remote.response.UpdateLocationResponse;

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

    @Override
    public Flowable<Boolean> updateProfileData(String sex, String career, String phone, String dir, String dis, String birthday){
        return Flowable.just(true);
    }
}
