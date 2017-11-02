package com.upc.location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.upc.location.R;

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
