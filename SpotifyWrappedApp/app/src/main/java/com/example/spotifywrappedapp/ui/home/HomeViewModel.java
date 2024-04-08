package com.example.spotifywrappedapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrappedapp.UserData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mText = new MutableLiveData<>();

        UserData userData = new UserData(application);
        mText.setValue("Welcome, "+userData.getDisplayName());
    }

    public LiveData<String> getText() {
        return mText;
    }
}