package com.example.locationdemo;

import android.location.Location;

import com.example.locationdemo.data.AppRepository;
import com.example.locationdemo.data.model.AppLocation;
import com.example.locationdemo.data.model.GeolocationError;
import com.example.locationdemo.data.model.GeolocationErrorTypes;
import com.example.locationdemo.utils.GPSTracker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subjects.PublishSubject;

public class LocationViewModel implements GPSTracker.LocationDelegate {

    AppRepository appRepository = new AppRepository();

    @Inject
    GPSTracker gpsTracker;

    public LocationViewModel() {
        App.get().getComponent().inject(this);
        gpsTracker.setLocationDelegate(this);
    }

    private PublishSubject<AppLocation> locationPublishSubject = PublishSubject.create();
    private PublishSubject<GeolocationError> errorsPublishSubject = PublishSubject.create();
    private Disposable periodicRequest;

    Flowable<AppLocation> getLocationChanges(){
        return locationPublishSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    Flowable<GeolocationError> getLocationErrors(){
        return errorsPublishSubject.toFlowable(BackpressureStrategy.BUFFER);
    }

    void start(){
        periodicRequest = Flowable
                .interval(0, 10, TimeUnit.SECONDS).timeInterval()
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(@NonNull Timed<Long> longTimed) throws Exception {
                        tryToGetLocation();
                    }
                });
    }

    private void tryToGetLocation(){
        if(!gpsTracker.canGetLocation()){
            publishError(GeolocationErrorTypes.GPS_NOT_ENABLED);
            return;
        }

        if(!gpsTracker.hasPermissions()){
            publishError(GeolocationErrorTypes.PERMISSION_NOT_ENABLED);
            return;
        }

        lastKnownLocation = gpsTracker.getLocation();
        if(lastKnownLocation!=null){
            AppLocation appLocation = new AppLocation();
            appLocation.setLatitude(lastKnownLocation.getLatitude());
            appLocation.setLongitude(lastKnownLocation.getLongitude());

            locationPublishSubject.onNext(appLocation);
            appRepository.updateLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        }else{
            publishError(GeolocationErrorTypes.NO_LOCATION);
        }
    }

    private void publishError(GeolocationErrorTypes type){
        errorsPublishSubject.onNext(new GeolocationError(type));
    }

    void stop(){
        if(gpsTracker != null)
            gpsTracker.stopUsingGPS();

        if(periodicRequest != null)
            periodicRequest.dispose();
    }

    void logout(){
        appRepository.logout();
    }

    private Location lastKnownLocation;
    @Override
    public void onLocationChanged(Location location) {
        lastKnownLocation = location;
    }
}
