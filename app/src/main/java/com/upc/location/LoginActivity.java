package com.upc.location;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.upc.location.data.AppRepository;
import com.upc.location.utils.FirebaseUtils;
import com.upc.location.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    AppRepository repository = new AppRepository();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressView = findViewById(R.id.login_progress);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            goToProfileScreen();
        } else {
            openFirebaseAuthUI();
        }

    }


    private static final int RC_SIGN_IN = 123;

    private void openFirebaseAuthUI(){
        FirebaseUtils.openAuthUI(this, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            repository.login();
            goToProfileScreen();
        }else{
            finish();
        }
    }

    private void goToProfileScreen(){
        SharedPreferences preferences = this.getSharedPreferences("app_prefs", 0);
        String career = preferences.getString("key_career", null);
        if(career == null){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
        finish();
    }

}

