package com.example.locationdemo.utils;

import android.app.Activity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

public class FirebaseUtils {

    public static void openAuthUI(Activity activity, int requestCode){
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                        .build(),
                requestCode);
    }
}
