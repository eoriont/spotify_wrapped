package com.example.spotifywrappedapp.ui.notifications;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.spotifywrappedapp.UserData;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecommendationsViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private Application application;

    public RecommendationsViewModel(Application app) {
        super(app);
        this.application = app;
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void performNetworkRequest() {
        mText.setValue("Loading...");

        UserData userData = new UserData(application);
        String id = userData.getId();

        final Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/v1/recommendation/" + id)
                .get()
                .addHeader("Authorization", "Bearer " + userData.getToken())
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call,
                                  @NonNull IOException e) {
                Log.d("backend", "failure! " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call,
                                   @NonNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        Log.d("backend", res);
                        mText.postValue(res);
                    } catch (Exception e) {
                        Log.d("backend", Log.getStackTraceString(e));
                    }
                }
            }
        });
    }
}
