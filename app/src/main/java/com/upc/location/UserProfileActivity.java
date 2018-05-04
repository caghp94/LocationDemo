package com.upc.location;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.upc.location.R;
import com.upc.location.data.AppRepository;
import com.upc.location.data.persistence.SharedPrefAppDao;

public class UserProfileActivity extends AppCompatActivity {

    Button btnContinue;
    Switch switchSex;
    EditText txtCareer, txtTlf, txtDir, txtDis, txtBirthDay, txtBirthMonth, txtBirthYear;
    AppRepository appRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnContinue = (Button) findViewById(R.id.btn_continue);
        switchSex = (Switch) findViewById(R.id.switch_sex);
        txtCareer = (EditText) findViewById(R.id.input_career);
        txtTlf = (EditText) findViewById(R.id.input_phone);
        txtDir = (EditText) findViewById(R.id.input_address);
        txtDis = (EditText) findViewById(R.id.input_address2);
        txtBirthDay = (EditText) findViewById(R.id.input_day);
        txtBirthMonth = (EditText) findViewById(R.id.input_month);
        txtBirthYear = (EditText) findViewById(R.id.input_year);

        appRepository = new AppRepository();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = (switchSex.isChecked()) ? "Female" : "Male";
                String career = txtCareer.getText().toString();
                String phone = txtTlf.getText().toString();
                String address1 = txtDir.getText().toString();
                String address2 = txtDis.getText().toString();
                String birthday = txtBirthDay.getText().toString() + "/" + txtBirthMonth.getText().toString() + "/" + txtBirthYear.getText().toString();

                appRepository.updateProfileData(sex, career, phone, address1, address2, birthday);
                SharedPrefAppDao sharedPrefAppDao = new SharedPrefAppDao(getApplicationContext());
                sharedPrefAppDao.saveProfile(sex, career, phone, address1, address2, birthday);

                goToMainScreen();
            }
        });

    }
    private void goToMainScreen(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

}
