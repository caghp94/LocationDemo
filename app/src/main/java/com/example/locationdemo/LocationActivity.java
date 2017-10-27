package com.example.locationdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.locationdemo.data.model.AppLocation;
import com.example.locationdemo.data.model.GeolocationError;
import com.example.locationdemo.data.model.GeolocationErrorTypes;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class LocationActivity extends AppCompatActivity {

    LocationViewModel viewModel;

    TextView latitudeTextView;
    TextView longitudeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }




}
