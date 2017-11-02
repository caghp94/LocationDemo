package com.upc.location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.upc.location.data.model.AppLocation;
import com.upc.location.data.model.GeolocationErrorTypes;
import com.upc.location.R;
import com.upc.location.data.model.GeolocationError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewModel = new LocationViewModel();
        bind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(pendingLocation != null)
            showLocation(pendingLocation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.stop();
    }

    private void bind(){
        viewModel.getLocationChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AppLocation>() {
                    @Override
                    public void accept(@NonNull AppLocation appLocation) throws Exception {
                        showLocation(appLocation);
                    }
                });

        viewModel.getLocationErrors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GeolocationError>() {
                    @Override
                    public void accept(@NonNull GeolocationError geolocationError) throws Exception {
                        handleError(geolocationError.getError());
                    }
                });
    }


    private AppLocation pendingLocation;
    void showLocation(AppLocation appLocation){
        pendingLocation = appLocation;
        if(mMap == null)
            return;

        // Add a marker in Sydney and move the camera
        mMap.clear();
        LatLng sydney = new LatLng(appLocation.getLatitude(), appLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Estás aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,17));

        pendingLocation = null;
    }

    private void handleError(GeolocationErrorTypes type){
        if(type == GeolocationErrorTypes.GPS_NOT_ENABLED){
            requestToEnableGPS();
        }else if(type == GeolocationErrorTypes.PERMISSION_NOT_ENABLED){
            requestPermissions();
        }else if(type == GeolocationErrorTypes.NO_LOCATION){
            showNoLocation();
        }
    }

    void showNoLocation(){

    }

    void requestToEnableGPS() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.gps_request_title));

        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.gps_request_message));

        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.txt_no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // On pressing Settings button
        alertDialog.setPositiveButton(getString(R.string.gps_activate),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    private boolean isRequestingPermission;
    void requestPermissions(){
        if(isRequestingPermission)
            return;

        isRequestingPermission = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isRequestingPermission = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        viewModel.logout();
        finish();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
