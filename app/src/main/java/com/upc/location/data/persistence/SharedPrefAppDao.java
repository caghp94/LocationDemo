package com.upc.location.data.persistence;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefAppDao implements AppDao{

    private static final String PREFERENCES = "app_prefs";

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PASS = "key_pass";

    private static final String KEY_SEX = "key_sex";
    private static final String KEY_CAREER = "key_career";
    private static final String KEY_PHONE = "key_phone";
    private static final String KEY_ADDRESS1 = "key_address1";
    private static final String KEY_ADDRESS2 = "key_address2";
    private static final String KEY_BIRTHDAY = "key_birthday";


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

    @Override
    public void saveProfile(String sex, String career, String phone, String address1, String address2, String birthday){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SEX, sex);
        editor.putString(KEY_CAREER, career);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_ADDRESS1, address1);
        editor.putString(KEY_ADDRESS2, address2);
        editor.putString(KEY_BIRTHDAY, birthday);
        editor.apply();

    }
}
