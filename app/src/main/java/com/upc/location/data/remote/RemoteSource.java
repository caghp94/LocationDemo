package com.upc.location.data.remote;

import com.upc.location.data.remote.request.LocationRequest;
import com.upc.location.data.remote.response.UpdateLocationResponse;


import io.reactivex.Flowable;


public interface RemoteSource {

    Flowable<UpdateLocationResponse> updateLocation(LocationRequest request);

    Flowable<Boolean> login();

    Flowable<Boolean> logout();

    Flowable<Boolean> updateProfileData(String sex, String career, String phone, String dir, String dis, String birthday);
}
