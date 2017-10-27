package com.example.locationdemo.data.persistence;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefAppDao implements AppDao{

    private static final String PREFERENCES = "app_prefs";

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASS = "key_pass";


    private SharedPreferences sharedPreferences;

    public SharedPrefAppDao(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
    }

    @Override
    public void saveSession(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASS, password);
        editor.apply();
    }
}
