package com.example.spotifywrappedapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mText = new MutableLiveData<>();

        UserData userData = new UserData(application);
        String id = userData.getId();
        mText.setValue("Welcome, " + id);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
