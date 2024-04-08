package com.example.spotifywrappedapp.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spotifywrappedapp.UserData;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mText = new MutableLiveData<>();

        UserData userData = new UserData(application);
        String id = userData.getId();
        mText.setValue("Welcome, "+id);

        // TODO: add more user details to endpoint
//        final Request request = new Request.Builder()
//                .url("http://10.0.2.2:8080/v1/user/"+id)
//                .get()
//                .build();
//
////        cancelCall();
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("backend", "failure! "+ e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                try {
//                    Log.d("JSON", response.body().string());
//                    JSONObject res = new JSONObject(response.body().string());
//                    String name = res.getString("display_name");
//                    mText.setValue("Welcome, "+userData.getId());
//                } catch (Exception e) {
//                    Log.e("JSON", e.getMessage());
//                }
//            }
//        });
//
    }

    public LiveData<String> getText() {
        return mText;
    }
}