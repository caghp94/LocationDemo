package com.example.locationdemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.locationdemo.data.AppRepository;
import com.example.locationdemo.data.remote.response.LoginResponse;
import com.example.locationdemo.utils.FirebaseUtils;
import com.example.locationdemo.utils.ViewUtils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
            goToMainScreen();
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
            goToMainScreen();
        }else{
            finish();
        }
    }

    private void goToMainScreen(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

}

