package com.example.spotifywrappedapp.ui.friends;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FriendsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> username = new MutableLiveData<>();

    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
        Log.d("Friends", username);
    }
}