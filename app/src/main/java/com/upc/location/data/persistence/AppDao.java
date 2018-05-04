package com.upc.location.data.persistence;

public interface AppDao {
    void saveSession(String username, String password);

    void saveProfile(String sex, String career, String phone, String address1, String address2, String birthday);
}
