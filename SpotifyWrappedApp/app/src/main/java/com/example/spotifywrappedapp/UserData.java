package com.example.spotifywrappedapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class UserData {
    private SharedPreferences sharedPreferences;

    public UserData(Application application) {
        sharedPreferences = application.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }

    public String getDisplayName() {
        return sharedPreferences.getString("display_name", "USERNAME_NOT_SET!!");
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString("token", token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "xxxx");
    }

    public void setId(String id) {
        sharedPreferences.edit().putString("id", id).apply();
    }

    public String getId() {
        return sharedPreferences.getString("id", "xxxxx");
    }

}
