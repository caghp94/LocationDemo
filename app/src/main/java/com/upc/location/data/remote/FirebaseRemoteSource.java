package com.upc.location.data.remote;

import android.net.Uri;

import com.upc.location.data.remote.request.LocationRequest;
import com.upc.location.data.remote.response.UpdateLocationResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;

public class FirebaseRemoteSource implements RemoteSource {


    // Write a message to the database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().child("users");

    @Override
    public Flowable<UpdateLocationResponse> updateLocation(@Body LocationRequest request) {
        String id = getUserId();
        if(id == null)
            return Flowable.just(new UpdateLocationResponse(false));


        Map<String, Object> lastTrack = new HashMap<>();
        //location.put("latitude", request.getLatitude());
        //location.put("longitude", request.getLongitude());
        lastTrack.put("lastTrackingDate", new Date().toString());

        myRef.child(id).updateChildren(lastTrack);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        String dateNow = sDateFormat.format(now);

        Map<String, Object> location = new HashMap<>();
        location.put("latitude", request.getLatitude());
        location.put("longitude", request.getLongitude());

        myRef.child(id).child("locations").child(dateNow).updateChildren(location);

        return Flowable.just(new UpdateLocationResponse(true));
    }

    @Override
    public Flowable<Boolean> login() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null)
            return Flowable.just(false);


        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("logged", true);


        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        Uri photoUrl = currentUser.getPhotoUrl();

        if(name != null && !name.isEmpty())
            userInfo.put("name", name);
        if(email != null && !email.isEmpty())
            userInfo.put("email", email);
        if(photoUrl != null)
            userInfo.put("photoUrl", photoUrl.toString());

        myRef.child(currentUser.getUid()).updateChildren(userInfo);


        return Flowable.just(true);
    }

    @Override
    public Flowable<Boolean> logout() {
        String id = getUserId();
        if(id == null)
            return Flowable.just(false);

        myRef.child(id).child("logged").setValue(false);
        FirebaseAuth.getInstance().signOut();

        return Flowable.just(true);
    }

    private String getUserId(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null)
            return currentUser.getUid();
        else
            return  null;
    }

    public Flowable<Boolean> updateProfileData(String sex, String career, String phone, String dir, String dis, String birthday) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null)
            return Flowable.just(false);


        Map<String, Object> profileData = new HashMap<>();

        profileData.put("sex", sex);
        profileData.put("career", career);
        profileData.put("phone", phone);
        profileData.put("address1", dir);
        profileData.put("address2", dis);
        profileData.put("birthday", birthday);

        myRef.child(currentUser.getUid()).updateChildren(profileData);

        return Flowable.just(true);
    }
}
